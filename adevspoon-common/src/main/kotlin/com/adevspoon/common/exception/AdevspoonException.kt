package com.adevspoon.common.exception


open class AdevspoonException(
    errorCode: AdevspoonErrorCode,
) : RuntimeException() {
    val errorInfo = errorCode.getErrorInfo()
}