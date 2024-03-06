package com.adevspoon.infrastructure.oauth.exception

import com.adevspoon.common.dto.ErrorInfo
import com.adevspoon.common.exception.AdevspoonErrorCode

enum class OAuthErrorCode(
    private val status: Int,
    private val code: Int,
    private val message: String,
): AdevspoonErrorCode {
    KAKAO_TOKEN_EMPTY(400, 400_999_000, "카카오 토큰 없음"),
    APPLE_TOKEN_EMPTY(400, 400_999_000, "애플 토큰 없음"),

    KAKAO_TOKEN_INVALID(400, 400_999_001, "카카오 토큰값이 잘못됨"),
    APPLE_TOKEN_HEADER_INVALID(400, 400_999_001, "애플 토큰 헤더값이 잘못됨"),
    APPLE_TOKEN_INVALID(400, 400_999_001, "애플 토큰값이 잘못됨"),

    KAKAO_SERVER_ERROR(500, 500_999_000, "카카오서버 오류"),
    APPLE_SERVER_ERROR(500, 500_999_000, "애플서버 오류");

    override fun getErrorInfo() = ErrorInfo(
        status = status,
        code = code,
        message = message
    )
}