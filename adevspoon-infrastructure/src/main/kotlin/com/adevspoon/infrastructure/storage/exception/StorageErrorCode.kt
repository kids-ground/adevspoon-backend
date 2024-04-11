package com.adevspoon.infrastructure.storage.exception


import com.adevspoon.common.exception.AdevspoonErrorCode
import com.adevspoon.common.exception.DomainType

enum class StorageErrorCode(
    override val error: DomainType.Error,
    override val message: String,
): AdevspoonErrorCode {
    S3_UPLOAD_ERROR(DomainType.EXTERNAL_STORAGE code 500 no 0, "버킷 업로드 실패");
}