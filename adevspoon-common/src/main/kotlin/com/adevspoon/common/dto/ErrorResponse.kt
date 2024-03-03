package com.adevspoon.common.dto


data class ErrorResponse(
    private val error: ErrorInfo
) {
    val code: Int = error.code
    val message: String = error.message
}