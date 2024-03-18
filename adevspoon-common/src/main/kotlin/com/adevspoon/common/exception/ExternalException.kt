package com.adevspoon.common.exception

open class ExternalException(
    private val errorCode: AdevspoonErrorCode,
    open val reason: String?,
): RuntimeException() {
    val errorInfo = errorCode.getErrorInfo()
}