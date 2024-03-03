package com.adevspoon.common.exception


abstract class AdevspoonException(
    private val errorCode: AdevspoonErrorCode,
) : RuntimeException() {
    val errorInfo = errorCode.getErrorInfo()
}