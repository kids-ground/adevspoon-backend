package com.adevspoon.common.exception.common

import com.adevspoon.common.exception.AdevspoonErrorCode
import com.adevspoon.common.exception.DomainType


enum class CommonErrorCode(
    override val error: DomainType.Error,
    override val message: String,
): AdevspoonErrorCode {
    BAD_REQUEST(DomainType.COMMON code 400 no 0, "잘못된 요청입니다"),

    UNAUTHORIZED(DomainType.COMMON code 401 no 0, "잘못된 인증 정보입니다"),

    FORBIDDEN(DomainType.COMMON code 403 no 0, "접근 권한이 없습니다"),

    NOT_FOUND(DomainType.COMMON code 404 no 0, "요청 정보를 찾을 수 없습니다"),

    INTERNAL_SERVER_ERROR(DomainType.COMMON code 500 no 0, "서버 내부 오류입니다. 관리자에게 문의하세요");
}