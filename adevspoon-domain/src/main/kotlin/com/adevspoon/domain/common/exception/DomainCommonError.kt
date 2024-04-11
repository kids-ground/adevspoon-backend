package com.adevspoon.domain.common.exception


import com.adevspoon.common.exception.AdevspoonErrorCode
import com.adevspoon.common.exception.DomainType

enum class DomainCommonError(
    override val error: DomainType.Error,
    override val message: String,
): AdevspoonErrorCode {
    INVALID_ATTRIBUTE(DomainType.COMMON code 500 no 1, "유효하지 않은 값 사용");
}