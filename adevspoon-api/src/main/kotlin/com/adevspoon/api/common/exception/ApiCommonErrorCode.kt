package com.adevspoon.api.common.exception


import com.adevspoon.common.exception.AdevspoonErrorCode

enum class ApiCommonErrorCode(
    override val status: Int,
    override val code: Int,
    override val message: String,
): AdevspoonErrorCode {
    INVALID_ENUM_VALUE(400, 400_000_001, "유효하지 않은 Enum 값입니다");
}