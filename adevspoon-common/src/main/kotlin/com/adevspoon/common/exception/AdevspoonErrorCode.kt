package com.adevspoon.common.exception

import com.adevspoon.common.dto.ErrorInfo

interface AdevspoonErrorCode {
    val status: Int
    val code: Int
    val message: String

    fun getErrorInfo() = ErrorInfo(status, code, message)
}