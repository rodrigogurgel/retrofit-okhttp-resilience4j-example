package br.com.rodrigogurgel.retrofitokhttpresilience4jexample.exception

import br.com.rodrigogurgel.retrofitokhttpresilience4jexample.model.exception.ResultError
import br.com.rodrigogurgel.retrofitokhttpresilience4jexample.model.exception.resultError
import br.com.rodrigogurgel.retrofitokhttpresilience4jexample.model.web.HttpErrorException
import io.github.resilience4j.circuitbreaker.CallNotPermittedException
import io.github.resilience4j.retry.MaxRetriesExceededException
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.ControllerAdvice
import org.springframework.web.bind.annotation.ExceptionHandler
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler

@ControllerAdvice
class ExceptionHandlerController : ResponseEntityExceptionHandler() {
    @ExceptionHandler(value = [HttpErrorException::class])
    fun handlerHttpErrorException(e: HttpErrorException): ResponseEntity<ResultError> {
        return ResponseEntity
            .status(e.status?.value() ?: HttpStatus.INTERNAL_SERVER_ERROR.value())
            .body(resultError {
                errorType = e.status ?: HttpStatus.INTERNAL_SERVER_ERROR
                description = "The service ${e.url} returned error ${e.status}"
                remoteErrorMessage = e.description
                code = null
            })
    }

    @ExceptionHandler(value = [CallNotPermittedException::class])
    fun handlerCallNotPermittedException(e: CallNotPermittedException): ResponseEntity<ResultError> {
        return ResponseEntity
            .status(HttpStatus.INTERNAL_SERVER_ERROR.value())
            .body(resultError {
                errorType = HttpStatus.INTERNAL_SERVER_ERROR
                description = e.message ?: e.localizedMessage
                remoteErrorMessage = null
                code = null
            })
    }

    @ExceptionHandler(value = [MaxRetriesExceededException::class])
    fun handlerMaxRetriesExceededException(e: MaxRetriesExceededException): ResponseEntity<ResultError> {
        return ResponseEntity
            .status(HttpStatus.INTERNAL_SERVER_ERROR.value())
            .body(resultError {
                errorType = HttpStatus.INTERNAL_SERVER_ERROR
                description = e.message ?: e.localizedMessage
                remoteErrorMessage = null
                code = null
            })
    }
}