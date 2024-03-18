package com.adevspoon.api.config.controller.advice

import com.adevspoon.common.dto.ErrorResponse
import com.adevspoon.common.exception.AdevspoonException
import com.adevspoon.common.exception.ExternalException
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

    @ExceptionHandler(ExternalException::class)
    fun handleExternalException(e: ExternalException): ResponseEntity<ErrorResponse> {
        logger.error("""
            message: ${e.message}
            reason: ${e.reason}
        """.trimIndent())
        val errorInfo = e.errorInfo
        return ResponseEntity
            .status(errorInfo.status)
            .body(ErrorResponse(errorInfo.code, errorInfo.message))
    }

    // TODO: - ErrorResponse 내 TraceID를 넘기는 것 고려
    @ExceptionHandler(Exception::class)
    fun handleUncaughtException(e: Exception): ResponseEntity<ErrorResponse> {
        logger.error(e.message)
        return ResponseEntity
            .status(500)
            .body(ErrorResponse(500, "Internal server error"))
    }
}