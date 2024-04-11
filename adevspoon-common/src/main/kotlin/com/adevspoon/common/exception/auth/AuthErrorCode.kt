package com.adevspoon.common.exception.auth

import com.adevspoon.common.exception.AdevspoonErrorCode
import com.adevspoon.common.exception.DomainType


enum class AuthErrorCode(
    override val error: DomainType.Error,
    override val message: String,
): AdevspoonErrorCode {
    MISSING_AUTH(DomainType.AUTH code 401 no 1, "인증정보가 없습니다"),

    ILLEGAL_AUTH_ARGUMENT_ERROR(DomainType.AUTH code 500 no 1, "서버 내부 오류입니다. 관리자에게 문의하세요");
}