package com.adevspoon.api.common.exception


import com.adevspoon.common.exception.AdevspoonErrorCode
import com.adevspoon.common.exception.DomainType

enum class ApiCommonErrorCode(
    override val error: DomainType.Error,
    override val message: String,
): AdevspoonErrorCode {
    INVALID_ENUM_VALUE(DomainType.COMMON code 400 no 1, "유효하지 않은 Enum 값입니다");
}