package com.adevspoon.domain.common.exception


import com.adevspoon.common.exception.AdevspoonErrorCode
import com.adevspoon.common.exception.DomainType

enum class DomainCommonError(
    override val error: DomainType.Error,
    override val message: String,
): AdevspoonErrorCode {
    INVALID_ATTRIBUTE(DomainType.COMMON code 500 no 1, "유효하지 않은 값 사용"),

    FAIL_TO_GET_LOCK(DomainType.COMMON code 500 no 2, "서버 내부 오류입니다. 관리자에게 문의하세요");
}