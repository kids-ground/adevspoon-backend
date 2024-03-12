package com.adevspoon.common.exception

import com.adevspoon.common.dto.ErrorInfo

enum class CommonErrorCode(
    private val status: Int,
    private val code: Int,
    private val message: String,
): AdevspoonErrorCode {
    // code = {status}_{service}_{num}
    BAD_REQUEST(400, 400_000_000, "잘못된 요청입니다"),
    REQUEST_FILE_NAME_EMPTY(400, 400_000_001, "파일 이름이 존재하지 않습니다"),
    REQUEST_FILE_EXTENSION_EMPTY(400, 400_000_001, "파일 확장자가 존재하지 않습니다"),

    UNAUTHORIZED(401, 401_000_000, "잘못된 인증 정보입니다"),
    MISSING_AUTH(401, 401_000_001, "인증정보가 없습니다"),

    FORBIDDEN(403, 403_000_000, "접근 권한이 없습니다"),

    NOT_FOUND(404, 404_000_000, "요청 정보를 찾을 수 없습니다"),

    INTERNAL_SERVER_ERROR(500, 500_000_000, "서버 내부 오류입니다. 관리자에게 문의하세요"),
    ILLEGAL_AUTH_ARGUMENT_ERROR(500, 500_000_001, "서버 내부 오류입니다. 관리자에게 문의하세요");



    override fun getErrorInfo() = ErrorInfo(
        status = status,
        code = code,
        message = message
    )
}