package com.adevspoon.common.exception

import com.adevspoon.common.dto.ErrorInfo

enum class FileErrorCode(
    private val status: Int,
    private val code: Int,
    private val message: String,
): AdevspoonErrorCode {
    FILE_NAME_EMPTY(400, 400_000_002, "파일 이름이 존재하지 않습니다"),
    FILE_EXTENSION_EMPTY(400, 400_000_002, "파일 확장자가 존재하지 않습니다"),
    FILE_EXTENSION_NOT_SUPPORT(400, 400_000_002, "지원하지 않는 파일 확장자입니다"),

    RESIZE_IMAGE_FAILED(500, 500_000_002, "이미지 리사이즈에 실패했습니다");

    override fun getErrorInfo() = ErrorInfo(
        status = status,
        code = code,
        message = message
    )
}