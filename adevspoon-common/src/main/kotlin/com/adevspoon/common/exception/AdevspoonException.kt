package com.adevspoon.common.exception

import com.adevspoon.common.dto.ErrorInfo


open class AdevspoonException(
    errorCode: AdevspoonErrorCode,
    detailReason: String? = null
) : RuntimeException(detailReason ?: errorCode.getErrorInfo().message) {
    val errorInfo = ErrorInfo(
        status = errorCode.getErrorInfo().status,
        code = errorCode.getErrorInfo().code,
        message = detailReason ?: errorCode.getErrorInfo().message
    )
}