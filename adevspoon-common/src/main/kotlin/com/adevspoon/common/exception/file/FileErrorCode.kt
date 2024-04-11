package com.adevspoon.common.exception.file

import com.adevspoon.common.exception.AdevspoonErrorCode
import com.adevspoon.common.exception.DomainType


enum class FileErrorCode(
    override val error: DomainType.Error,
    override val message: String,
): AdevspoonErrorCode {
    FILE_NAME_EMPTY(DomainType.COMMON code 400 no 2, "파일 이름이 존재하지 않습니다"),
    FILE_EXTENSION_EMPTY(DomainType.COMMON code 400 no 2, "파일 확장자가 존재하지 않습니다"),
    FILE_EXTENSION_NOT_SUPPORT(DomainType.COMMON code 400 no 2, "지원하지 않는 파일 확장자입니다"),

    RESIZE_IMAGE_FAILED(DomainType.COMMON code 500 no 2, "이미지 리사이즈에 실패했습니다");
}