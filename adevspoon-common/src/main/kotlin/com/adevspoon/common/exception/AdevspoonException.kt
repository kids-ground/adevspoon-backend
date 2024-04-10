package com.adevspoon.common.exception


open class AdevspoonException(
    errorCode: AdevspoonErrorCode,
    detailReason: String? = null
) : RuntimeException() {
    val errorInfo = errorCode.getErrorInfo()
        .apply { message = detailReason ?: message }
}