package br.com.rodrigogurgel.retrofitokhttpresilience4jexample.config.web

import com.fasterxml.jackson.annotation.JsonInclude
import com.fasterxml.jackson.core.JsonParser
import com.fasterxml.jackson.databind.ObjectMapper
import com.fasterxml.jackson.datatype.jdk8.Jdk8Module
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule
import com.fasterxml.jackson.module.kotlin.KotlinModule
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration

@Configuration
class JacksonConfiguration {
    @Bean
    fun objectMapper() : ObjectMapper =
        with(ObjectMapper()) {
            configure(JsonParser.Feature.ALLOW_UNQUOTED_FIELD_NAMES, true)
            setSerializationInclusion(JsonInclude.Include.NON_NULL)
            registerModule(JavaTimeModule())
            registerModule(KotlinModule.Builder().build())
            registerModule(Jdk8Module())
        }
}