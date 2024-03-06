package com.adevspoon.common.exception

import com.adevspoon.common.dto.ErrorInfo

enum class CommonErrorCode(
    private val status: Int,
    private val code: Int,
    private val message: String,
): AdevspoonErrorCode {
    // code = {status}_{service}_{num}
    BAD_REQUEST(400, 400_000_000, "잘못된 요청입니다"),
    UNAUTHORIZED(401, 401_000_000, "잘못된 인증 정보입니다"),
    FORBIDDEN(403, 403_000_000, "권한이 없습니다"),
    NOT_FOUND(404, 404_000_000, "요청 정보를 찾을 수 없습니다");

    override fun getErrorInfo() = ErrorInfo(
        status = status,
        code = code,
        message = message
    )
}