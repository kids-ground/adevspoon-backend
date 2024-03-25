package com.adevspoon.api.config.controller.advice


import com.adevspoon.common.dto.ErrorResponse
import com.adevspoon.common.exception.AdevspoonException
import com.adevspoon.common.exception.CommonErrorCode
import com.adevspoon.common.exception.ExternalException
import com.fasterxml.jackson.databind.exc.MismatchedInputException
import org.springframework.http.HttpHeaders
import org.springframework.http.HttpStatus
import org.springframework.http.HttpStatusCode
import org.springframework.http.ResponseEntity
import org.springframework.http.converter.HttpMessageNotReadableException
import org.springframework.lang.Nullable
import org.springframework.web.bind.MethodArgumentNotValidException
import org.springframework.web.bind.annotation.ExceptionHandler
import org.springframework.web.bind.annotation.RestControllerAdvice
import org.springframework.web.context.request.WebRequest
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler


@RestControllerAdvice
class GlobalExceptionHandler: ResponseEntityExceptionHandler() {
    // Validation & Query Parameter 바인딩(ex. enum) 예외
    override fun handleMethodArgumentNotValid(
        ex: MethodArgumentNotValidException,
        headers: HttpHeaders,
        status: HttpStatusCode,
        request: WebRequest
    ): ResponseEntity<Any>? {
        val errorMessage = ex.bindingResult.fieldErrors.joinToString(", ") { it.field } + "필드의 값이 잘못되었습니다."
        logger.warn("잘못된 요청 정보 : $errorMessage")
        return ResponseEntity
            .status(HttpStatus.BAD_REQUEST)
            .body(ErrorResponse(
                CommonErrorCode.BAD_REQUEST.getErrorInfo().code,
                errorMessage
            ))
    }

    // JSON 파싱에서의 예외
    override fun handleHttpMessageNotReadable(
        ex: HttpMessageNotReadableException,
        headers: HttpHeaders,
        status: HttpStatusCode,
        request: WebRequest
    ): ResponseEntity<Any>? {
        val errorInfo = when (val cause = ex.rootCause) {
            is AdevspoonException -> cause.errorInfo
            is MismatchedInputException -> CommonErrorCode.BAD_REQUEST
                .getErrorInfo()
                .also { it.message = "유효하지 않은 값입니다: ${cause.path.map { it.fieldName }.joinToString(".")}" }
            else -> CommonErrorCode.BAD_REQUEST.getErrorInfo()
        }
        logger.warn("잘못된 요청 타입매핑 : ${errorInfo.message}")
        return ResponseEntity
            .status(errorInfo.status)
            .body(ErrorResponse(errorInfo.code, errorInfo.message))
    }

    override fun handleExceptionInternal(
        ex: java.lang.Exception,
        @Nullable body: Any?,
        headers: HttpHeaders,
        statusCode: HttpStatusCode,
        request: WebRequest
    ): ResponseEntity<Any>? {
        logger.warn("Web MVC 예외 : ${ex.message}")
        return ResponseEntity
            .status(statusCode.value())
            .body(ErrorResponse(statusCode.value(), ex.message ?: ""))
    }

    @ExceptionHandler(AdevspoonException::class)
    fun handleAdevspoonException(e: AdevspoonException): ResponseEntity<ErrorResponse> {
        logger.warn("인 앱 예외 발생 : ${e.errorInfo.message}")
        val errorInfo = e.errorInfo
        return ResponseEntity
            .status(errorInfo.status)
            .body(ErrorResponse(errorInfo.code, errorInfo.message))
    }

    @ExceptionHandler(ExternalException::class)
    fun handleExternalException(e: ExternalException): ResponseEntity<ErrorResponse> {
        logger.error("""
            [외부 시스템 예외]
            message: ${e.message}
            reason: ${e.reason}
        """.trimIndent())
        val errorInfo = e.errorInfo
        return ResponseEntity
            .status(errorInfo.status)
            .body(ErrorResponse(errorInfo.code, errorInfo.message))
    }

    // TODO: - ErrorResponse 내 TraceID를 넘기는 것도 고려해보기
    @ExceptionHandler(Exception::class)
    fun handleUncaughtException(e: Exception): ResponseEntity<ErrorResponse> {
        logger.error("""
            [에러]
            message: ${e.message}
            type: ${e.javaClass.simpleName}
        """.trimIndent())
        return ResponseEntity
            .status(500)
            .body(ErrorResponse(500, "Internal server error"))
    }
}