package com.adevspoon.common.outer.oauth.dto

data class OAuthUserInfoRequest(
    val type: OAuthType,
    val token: String
)

enum class OAuthType {
    KAKAO, APPLE
}