package com.adevspoon.infrastructure.oauth.exception


import com.adevspoon.common.exception.AdevspoonErrorCode
import com.adevspoon.common.exception.DomainType

enum class OAuthErrorCode(
    override val error: DomainType.Error,
    override val message: String,
): AdevspoonErrorCode {
    KAKAO_TOKEN_EMPTY(DomainType.EXTERNAL_OAUTH code 400 no 0, "카카오 토큰 없음"),
    APPLE_TOKEN_EMPTY(DomainType.EXTERNAL_OAUTH code 400 no 0, "애플 토큰 없음"),

    KAKAO_TOKEN_INVALID(DomainType.EXTERNAL_OAUTH code 400 no 1, "카카오 토큰값이 잘못됨"),
    APPLE_TOKEN_HEADER_INVALID(DomainType.EXTERNAL_OAUTH code 400 no 1, "애플 토큰 헤더값이 잘못됨"),
    APPLE_TOKEN_INVALID(DomainType.EXTERNAL_OAUTH code 400 no 1, "애플 토큰값이 잘못됨"),

    KAKAO_SERVER_ERROR(DomainType.EXTERNAL_OAUTH code 500 no 0, "카카오서버 오류"),
    APPLE_SERVER_ERROR(DomainType.EXTERNAL_OAUTH code 500 no 0, "애플서버 오류");
}