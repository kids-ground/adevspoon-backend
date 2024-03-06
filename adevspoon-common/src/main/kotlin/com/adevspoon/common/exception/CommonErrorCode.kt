package com.adevspoon.common.exception

import com.adevspoon.common.dto.ErrorInfo

enum class CommonErrorCode(
    private val status: Int,
    private val code: Int,
    private val message: String,
): AdevspoonErrorCode {
    // code = {status}_{service}_{num}
    BAD_REQUEST(400, 400_000_000, "요청이 잘못됨"),
    UNAUTHORIZED(401, 401_000_000, "인증 정보 없음"),
    FORBIDDEN(403, 403_000_000, "권한 없음"),
    NOT_FOUND(404, 404_000_000, "요청 정보를 찾을 수 없음");

    override fun getErrorInfo() = ErrorInfo(
        status = status,
        code = code,
        message = message
    )
}