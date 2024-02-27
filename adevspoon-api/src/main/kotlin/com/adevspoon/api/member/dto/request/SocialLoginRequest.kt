package com.adevspoon.api.member.dto.request

import com.adevspoon.domain.user.domain.enums.UserOAuth
import com.adevspoon.infrastructure.oauth.dto.OAuthType
import com.fasterxml.jackson.annotation.JsonCreator
import java.util.*

data class SocialLoginRequest(
    val oauthToken: String,
    val loginType: SocialLoginType,
)

enum class SocialLoginType {
    KAKAO,
    APPLE;

    companion object {
        @JsonCreator
        fun fromString(value: String): SocialLoginType {
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