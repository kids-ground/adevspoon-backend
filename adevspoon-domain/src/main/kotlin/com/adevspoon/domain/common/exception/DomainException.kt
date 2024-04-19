package com.adevspoon.domain.common.exception

import com.adevspoon.common.exception.AdevspoonException

class DomainInvalidAttributeException(type: String? = null): AdevspoonException(DomainCommonError.INVALID_ATTRIBUTE, type?.let { "$it 타입 필드에 유효하지 않은 값이 들어갔습니다" })
class DomainFailToLockQueryException: AdevspoonException(DomainCommonError.FAIL_TO_LOCK_QUERY)
class DomainFailToGetLockException(keyName: String? = null, reason: String? = null): AdevspoonException(DomainCommonError.FAIL_TO_GET_LOCK, internalLog = keyName?.let { "$it 획득 실패 (사유: $reason)" })
class DomainFailToReleaseLockException(keyName: String? = null, reason: String? = null): AdevspoonException(DomainCommonError.FAIL_TO_RELEASE_LOCK, internalLog = keyName?.let { "$it 해제 실패 (사유: $reason)" })
class DomainLockKeyNotSetException: AdevspoonException(DomainCommonError.LOCK_KEY_NOT_SET)