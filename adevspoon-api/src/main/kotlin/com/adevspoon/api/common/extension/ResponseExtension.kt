package com.adevspoon.api.common.extension

import com.adevspoon.common.dto.ErrorInfo
import com.adevspoon.common.dto.ErrorResponse
import com.adevspoon.common.exception.AdevspoonErrorCode
import com.fasterxml.jackson.databind.ObjectMapper
import jakarta.servlet.http.HttpServletResponse
import org.springframework.http.MediaType

fun HttpServletResponse.writeErrorResponse(errorCode: AdevspoonErrorCode, objectMapper: ObjectMapper?) {
    this.characterEncoding = "UTF-8"
    this.contentType = MediaType.APPLICATION_JSON_VALUE
    this.writer.write(
        errorCode
            .getErrorInfo()
            .also { this.status = it.status }
            .let { ErrorResponse(it.code, it.message) }
            .let {
                objectMapper?.writeValueAsString(it) ?: ObjectMapper().writeValueAsString(it)
            }
    )
}

fun HttpServletResponse.writeErrorResponse(errorInfo: ErrorInfo, objectMapper: ObjectMapper?) {
    this.characterEncoding = "UTF-8"
    this.contentType = MediaType.APPLICATION_JSON_VALUE
    this.status = errorInfo.status

    val errorResponse = ErrorResponse(errorInfo.code, errorInfo.message)
    this.writer.write(
        objectMapper?.writeValueAsString(errorResponse) ?: ObjectMapper().writeValueAsString(errorResponse)
    )
}