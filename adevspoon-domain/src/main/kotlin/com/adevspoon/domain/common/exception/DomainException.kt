package com.adevspoon.domain.common.exception

import com.adevspoon.common.exception.AdevspoonException

class DomainInvalidAttributeException(type: String? = null): AdevspoonException(DomainCommonError.INVALID_ATTRIBUTE, type?.let { "$it 타입 필드에 유효하지 않은 값이 들어갔습니다" })
class DomainFailToGetLockException: AdevspoonException(DomainCommonError.FAIL_TO_GET_LOCK)