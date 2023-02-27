package br.com.rodrigogurgel.retrofitokhttpresilience4jexample.model.exception

import org.springframework.http.HttpStatus

data class ResultError (
    var errorType: HttpStatus = HttpStatus.INTERNAL_SERVER_ERROR,
    var description: String = "Unexpected error",
    var code: String? = null,
    var remoteErrorMessage: String? = null
)

fun resultError(block: ResultError.() -> Unit): ResultError = ResultError().apply(block)