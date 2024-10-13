package com.kyler.assignment.advice

import com.kyler.assignment.dto.ErrorResponse
import com.kyler.assignment.exception.KylerException
import jakarta.servlet.http.HttpServletRequest
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.ControllerAdvice
import org.springframework.web.bind.annotation.ExceptionHandler
import org.springframework.web.server.ResponseStatusException

@ControllerAdvice
class ExceptionControllerAdvice {
    @ExceptionHandler(value = [Exception::class])
    fun handleException(req: HttpServletRequest, ex: Exception): ResponseEntity<ErrorResponse> {
        val status = when (ex) {
            is ResponseStatusException -> ex.statusCode
            else -> HttpStatus.INTERNAL_SERVER_ERROR
        }
        val errorMessage = ErrorResponse(
            status.value().toString(),
            ex.message ?: HttpStatus.INTERNAL_SERVER_ERROR.reasonPhrase
        )
        return ResponseEntity(errorMessage, status)
    }


    @ExceptionHandler(value = [KylerException::class])
    fun handleKylerException(req: HttpServletRequest, ex: KylerException): ResponseEntity<ErrorResponse> {
        val errorMessage = ErrorResponse(
            ex.errorCode,
            ex.message
        )
        return ResponseEntity(errorMessage, ex.status)
    }
}