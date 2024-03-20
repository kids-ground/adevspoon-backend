package com.adevspoon.api.config.controller.advice

import com.adevspoon.common.dto.ErrorResponse
import com.adevspoon.common.exception.AdevspoonException
import com.adevspoon.common.exception.CommonErrorCode
import com.adevspoon.common.exception.ExternalException
import org.springframework.http.HttpHeaders
import org.springframework.http.HttpStatusCode
import org.springframework.http.ResponseEntity
import org.springframework.lang.Nullable
import org.springframework.web.bind.MethodArgumentNotValidException
import org.springframework.web.bind.annotation.ExceptionHandler
import org.springframework.web.bind.annotation.RestControllerAdvice
import org.springframework.web.context.request.WebRequest
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler
import java.net.HttpURLConnection.HTTP_BAD_REQUEST


@RestControllerAdvice
class GlobalExceptionHandler: ResponseEntityExceptionHandler() {

    // MVC 처리 예외 래핑처리
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

    // Validation 예외 처리
    @ExceptionHandler(MethodArgumentNotValidException::class)
    fun validException(e: MethodArgumentNotValidException): ResponseEntity<ErrorResponse?> {
        return ResponseEntity
            .status(HTTP_BAD_REQUEST)
            .body(ErrorResponse(
                CommonErrorCode.BAD_REQUEST.getErrorInfo().code,
                e.bindingResult.allErrors.first()?.defaultMessage ?: ""
            ))
    }

    // 앱 내 RuntimeException 처리
    @ExceptionHandler(AdevspoonException::class)
    fun handleAdevspoonException(e: AdevspoonException): ResponseEntity<ErrorResponse> {
        val errorInfo = e.errorInfo
        return ResponseEntity
            .status(errorInfo.status)
            .body(ErrorResponse(errorInfo.code, errorInfo.message))
    }

    // 외부시스템 사용시 예외처리
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

    // TODO: - ErrorResponse 내 TraceID를 넘기는 것도 고려해보기
    // 처리하지 못한 에러 처리
    @ExceptionHandler(Exception::class)
    fun handleUncaughtException(e: Exception): ResponseEntity<ErrorResponse> {
        logger.error("""
            message: ${e.message}
            type: ${e.javaClass.simpleName}
        """.trimIndent())
        return ResponseEntity
            .status(500)
            .body(ErrorResponse(500, "Internal server error"))
    }
}