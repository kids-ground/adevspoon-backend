package com.adevspoon.api.member.dto.request

import com.adevspoon.domain.member.domain.enums.UserOAuth
import com.adevspoon.infrastructure.oauth.dto.OAuthType
import com.fasterxml.jackson.annotation.JsonCreator

data class SocialLoginRequest(
    val oauthToken: String,
    val loginType: SocialLoginType,
)

enum class SocialLoginType {
    KAKAO,
    APPLE;

    companion object {
        @JsonCreator
        @JvmStatic
        fun fromString(value: String): SocialLoginType {
            System.out.println(value.uppercase())
            return valueOf(value.uppercase())
        }
    }

    fun toUserOAuth(): UserOAuth {
        return when (this) {
            KAKAO -> UserOAuth.kakao
            APPLE -> UserOAuth.apple
        }
    }

    fun toOAuthType(): OAuthType {
        return when (this) {
            KAKAO -> OAuthType.KAKAO
            APPLE -> OAuthType.APPLE
        }
    }
}