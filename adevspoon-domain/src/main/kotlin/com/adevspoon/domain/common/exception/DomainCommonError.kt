package com.adevspoon.domain.common.exception

import com.adevspoon.common.dto.ErrorInfo
import com.adevspoon.common.exception.AdevspoonErrorCode

enum class DomainCommonError(
    private val status: Int,
    private val code: Int,
    private val message: String,
): AdevspoonErrorCode {
    // code = {status}_{service|domain}_{num}
    INVALID_ATTRIBUTE(500, 500_000_001, "유효하지 않은 값 사용");

    override fun getErrorInfo() = ErrorInfo(
        status = status,
        code = code,
        message = message
    )
}