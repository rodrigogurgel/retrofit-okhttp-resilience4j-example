package br.com.rodrigogurgel.retrofitokhttpresilience4jexample.config.client

class ClientConfiguration (
    var url: String = "",
    var connectionTimeoutInMilliseconds: Long = 0,
    var readTimeoutInMilliseconds: Long = 0,
    var writeTimeoutInMilliseconds: Long = 0
)