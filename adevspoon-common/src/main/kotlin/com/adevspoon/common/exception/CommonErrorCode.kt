package com.adevspoon.common.exception

import com.adevspoon.common.dto.ErrorInfo

enum class CommonErrorCode(
    private val status: Int,
    private val code: Int,
    private val message: String,
): AdevspoonErrorCode {
    BAD_REQUEST(400, 400, "요청이 잘못됨"),
    UNAUTHORIZED(401, 401, "인증 정보 없음"),
    FORBIDDEN(403, 403, "권한 없음"),
    NOT_FOUND(404, 404, "요청 정보를 찾을 수 없음");

    override fun getErrorInfo() = ErrorInfo(
        status = status,
        code = code,
        message = message
    )
}