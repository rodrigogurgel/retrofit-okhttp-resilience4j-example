package br.com.rodrigogurgel.retrofitokhttpresilience4jexample.config.client.pokemon

import br.com.rodrigogurgel.retrofitokhttpresilience4jexample.client.pokemon.PokemonClient
import br.com.rodrigogurgel.retrofitokhttpresilience4jexample.config.client.ClientProperties
import com.fasterxml.jackson.databind.ObjectMapper
import io.github.resilience4j.circuitbreaker.CircuitBreaker
import io.github.resilience4j.circuitbreaker.CircuitBreakerConfig
import io.github.resilience4j.ratelimiter.RateLimiter
import io.github.resilience4j.ratelimiter.RateLimiterConfig
import io.github.resilience4j.retrofit.CircuitBreakerCallAdapter
import io.github.resilience4j.retrofit.RateLimiterCallAdapter
import io.github.resilience4j.retrofit.RetryCallAdapter
import io.github.resilience4j.retry.Retry
import io.github.resilience4j.retry.RetryConfig
import okhttp3.OkHttpClient
import org.springframework.beans.factory.annotation.Qualifier
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.context.annotation.Primary
import org.springframework.http.HttpStatus
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.jackson.JacksonConverterFactory
import java.time.Duration
import java.util.concurrent.TimeUnit

@Configuration
class PokemonClientConfiguration {

    private val circuitBreaker: CircuitBreaker = CircuitBreaker.of("pokemonClient") {
        CircuitBreakerConfig.Builder()
            .failureRateThreshold(10F)
            .minimumNumberOfCalls(10)
            .maxWaitDurationInHalfOpenState(Duration.ofSeconds(10))
            .writableStackTraceEnabled(true)
            .build()
    }

    private val retry: Retry = Retry.of("pokemonClient") {
        RetryConfig.Builder<Response<*>>()
            .waitDuration(Duration.ofMillis(100))
            .maxAttempts(3)
            .retryOnResult { r: Response<*> -> HttpStatus.valueOf(r.code()).isError }
            .failAfterMaxAttempts(false)
            .writableStackTraceEnabled(true)
            .build()
    }

    private val rateLimiter: RateLimiter = RateLimiter.of("pokemonClient") {
        RateLimiterConfig.Builder()
            .limitForPeriod(10)
            .limitRefreshPeriod(Duration.ofSeconds(10))
            .timeoutDuration(Duration.ofMillis(100))
            .writableStackTraceEnabled(true)
            .build()
    }

    @Bean("pokemonOkHttpClient")
    @Primary
    fun pokemonOkHttpClient(clientProperties: ClientProperties): OkHttpClient =
        with(clientProperties.pokemon) {
            OkHttpClient.Builder()
                .connectTimeout(connectionTimeoutInMilliseconds, TimeUnit.MILLISECONDS)
                .readTimeout(readTimeoutInMilliseconds, TimeUnit.MILLISECONDS)
                .writeTimeout(writeTimeoutInMilliseconds, TimeUnit.MILLISECONDS)
                .followRedirects(true)
                .followSslRedirects(true)
                .retryOnConnectionFailure(true)
                .cache(null)
                .build()
        }


    @Bean
    fun pokemonClient(
        @Qualifier("pokemonOkHttpClient")
        okHttpClient: OkHttpClient,
        clientProperties: ClientProperties,
        objectMapper: ObjectMapper
    ): PokemonClient = with(clientProperties.pokemon) {
        Retrofit.Builder()
            .addCallAdapterFactory(CircuitBreakerCallAdapter.of(circuitBreaker) { r: Response<*> -> HttpStatus.valueOf(r.code()).is2xxSuccessful })
            .addCallAdapterFactory(RetryCallAdapter.of(retry))
            .addCallAdapterFactory(RateLimiterCallAdapter.of(rateLimiter))
            .baseUrl(url)
            .client(okHttpClient)
            .addConverterFactory(JacksonConverterFactory.create(objectMapper))
            .build()
            .create(PokemonClient::class.java)
    }
}