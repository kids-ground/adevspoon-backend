package com.adevspoon.common.dto

data class ErrorInfo(
    val status: Int, // HTTP status code
    val code: Int, // error code
    val message: String, // error message
)
