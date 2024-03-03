package com.adevspoon.common.exception


class AdevspoonException(
    private val errorCode: AdevspoonErrorCode,
) : RuntimeException() {
    val errorInfo = errorCode.getErrorInfo()
}