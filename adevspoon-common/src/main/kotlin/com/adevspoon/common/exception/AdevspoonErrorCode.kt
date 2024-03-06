package com.adevspoon.common.exception

import com.adevspoon.common.dto.ErrorInfo

interface AdevspoonErrorCode {
    fun getErrorInfo(): ErrorInfo
    fun getException(): AdevspoonException = AdevspoonException(this)
}