package com.adevspoon.common.exception

open class ExternalException(
    errorCode: AdevspoonErrorCode,
    open val reason: String?,
): RuntimeException() {
    val errorInfo = errorCode.errorInfo
}