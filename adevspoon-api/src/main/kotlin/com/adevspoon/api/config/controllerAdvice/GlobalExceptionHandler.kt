package com.adevspoon.api.config.controllerAdvice

import com.adevspoon.common.dto.ErrorResponse
import com.adevspoon.common.dto.SuccessResponse
import com.adevspoon.common.exception.AdevspoonException
import org.slf4j.LoggerFactory
import org.springframework.http.HttpHeaders
import org.springframework.http.HttpStatusCode
import org.springframework.http.ResponseEntity
import org.springframework.lang.Nullable
import org.springframework.web.bind.annotation.ExceptionHandler
import org.springframework.web.bind.annotation.RestControllerAdvice
import org.springframework.web.context.request.WebRequest
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler

@RestControllerAdvice
class GlobalExceptionHandler: ResponseEntityExceptionHandler() {

    override fun handleExceptionInternal(
        ex: java.lang.Exception,
        @Nullable body: Any?,
        headers: HttpHeaders,
        statusCode: HttpStatusCode,
        request: WebRequest
    ): ResponseEntity<Any>? {
        return ResponseEntity
            .status(statusCode.value())
            .body(ErrorResponse(statusCode.value(), ex.message ?: ""))
    }

    @ExceptionHandler(AdevspoonException::class)
    fun handleAdevspoonException(e: AdevspoonException): ResponseEntity<ErrorResponse> {
        val errorInfo = e.errorInfo
        return ResponseEntity
            .status(errorInfo.status)
            .body(ErrorResponse(errorInfo.code, errorInfo.message))
    }

    @ExceptionHandler(Exception::class)
    fun handleUncaughtException(e: Exception): ResponseEntity<ErrorResponse> {
        logger.error(e.message)
        return ResponseEntity
            .status(500)
            .body(ErrorResponse(500, "Internal server error"))
    }
}