package com.adevspoon.common.exception

class ExternalException(
    private val errorCode: AdevspoonErrorCode,
    val reason: String,
): RuntimeException() {
    val errorInfo = errorCode.getErrorInfo()
}