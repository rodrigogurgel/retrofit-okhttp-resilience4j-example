package br.com.rodrigogurgel.retrofitokhttpresilience4jexample.config.client

import org.springframework.boot.context.properties.ConfigurationProperties
import org.springframework.context.annotation.Configuration

@Configuration
@ConfigurationProperties(prefix = "client")
class ClientProperties {
    val pokemon: ClientConfiguration = ClientConfiguration()
}