package com.adevspoon.common.exception


abstract class AdevspoonException(
    errorCode: AdevspoonErrorCode,
    detailReason: String? = null
) : RuntimeException() {
    val errorInfo = errorCode.errorInfo
        .apply { message = detailReason ?: message }
}