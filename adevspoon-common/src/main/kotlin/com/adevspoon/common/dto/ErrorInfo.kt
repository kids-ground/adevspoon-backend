package com.adevspoon.common.dto

data class ErrorInfo(
    val status: Int, // HTTP status code
    val code: Int, // error code
    var message: String, // error message
    var log: String? = null // log message
)
