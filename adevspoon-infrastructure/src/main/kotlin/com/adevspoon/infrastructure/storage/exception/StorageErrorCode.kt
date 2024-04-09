package com.adevspoon.infrastructure.storage.exception


import com.adevspoon.common.exception.AdevspoonErrorCode

enum class StorageErrorCode(
    override val status: Int,
    override val code: Int,
    override val message: String,
): AdevspoonErrorCode {
    S3_UPLOAD_ERROR(500, 500_998_000, "버킷 업로드 실패");
}