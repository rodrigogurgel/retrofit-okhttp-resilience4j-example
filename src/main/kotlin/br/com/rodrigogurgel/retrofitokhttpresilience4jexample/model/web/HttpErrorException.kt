package br.com.rodrigogurgel.retrofitokhttpresilience4jexample.model.web

import org.springframework.http.HttpStatus
import retrofit2.Response

class HttpErrorException(
    val url: String? = null,
    val status: HttpStatus?,
    val description: String? = null,
    val body: String? = null
): RuntimeException(description) {
    companion object {
        fun decode(response: Response<*>): HttpErrorException {
            return if(response.code() in 500..599) {
                HttpErrorException(
                    url = response.raw().request.url.toString(),
                    status = HttpStatus.resolve(response.code()),
                    description = "${response.raw().request.url} - ${response.message().ifEmpty { "No message" }} - ${response.code()}",
                    body = response.errorBody()?.string()
                )
            } else {
                HttpErrorException(
                    url = response.raw().request.url.toString(),
                    status = HttpStatus.resolve(response.code()),
                    description = "${response.raw().request.url} - ${response.message().ifEmpty { "No message" }} - ${response.code()}",
                    body = response.errorBody()?.string()
                )
            }
        }
    }
}