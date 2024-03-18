package com.adevspoon.infrastructure.storage.exception

import com.adevspoon.common.dto.ErrorInfo
import com.adevspoon.common.exception.AdevspoonErrorCode

enum class StorageErrorCode(
    private val status: Int,
    private val code: Int,
    private val message: String,
): AdevspoonErrorCode {
    S3_UPLOAD_ERROR(500, 500_998_000, "버킷 업로드 실패");

    override fun getErrorInfo() = ErrorInfo(
        status = status,
        code = code,
        message = message
    )
}