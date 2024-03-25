package com.adevspoon.api.common.exception

import com.adevspoon.common.exception.AdevspoonException

class ApiInvalidEnumException(fieldName: String): AdevspoonException(ApiCommonErrorCode.INVALID_ENUM_VALUE, "$fieldName 필드에 유효하지 않은 Enum 값이 포함되어 있습니다")