package com.adevspoon.common.exception

import com.adevspoon.common.dto.ErrorInfo

interface AdevspoonErrorCode {
    val error: DomainType.Error
    val message: String

    val errorInfo: ErrorInfo
        get() = ErrorInfo(error.status, error.errorCode, message)
}