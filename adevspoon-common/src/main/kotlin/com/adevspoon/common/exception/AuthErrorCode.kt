package com.adevspoon.common.exception

import com.adevspoon.common.dto.ErrorInfo

enum class AuthErrorCode(
    private val status: Int,
    private val code: Int,
    private val message: String,
): AdevspoonErrorCode {
    MISSING_AUTH(401, 401_000_001, "인증정보가 없습니다"),

    ILLEGAL_AUTH_ARGUMENT_ERROR(500, 500_000_001, "서버 내부 오류입니다. 관리자에게 문의하세요");

    override fun getErrorInfo() = ErrorInfo(
        status = status,
        code = code,
        message = message
    )
}