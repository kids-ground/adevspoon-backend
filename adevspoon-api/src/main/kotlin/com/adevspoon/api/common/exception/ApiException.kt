package com.adevspoon.api.common.exception

import com.adevspoon.common.exception.AdevspoonException

class ApiInvalidEnumException(fieldName: String? = null): AdevspoonException(ApiCommonErrorCode.INVALID_ENUM_VALUE, fieldName?.let { "$it 필드에 유효하지 않은 값이 들어갔습니다" })