package com.adevspoon.api.member.dto.request

import com.adevspoon.common.enums.SocialType
import com.adevspoon.domain.member.domain.enums.UserOAuth
import com.adevspoon.infrastructure.oauth.dto.OAuthUserInfoRequest
import com.fasterxml.jackson.annotation.JsonCreator

data class SocialLoginRequest(
    val oauthToken: String,
    val loginType: SocialType,
) {
    fun toOAuthUserInfoRequest(): OAuthUserInfoRequest =
        OAuthUserInfoRequest(
            type = loginType,
            kakaoAccessToken = if (loginType == SocialType.KAKAO) oauthToken else null,
            appleIdentityToken = if (loginType == SocialType.APPLE) oauthToken else null,
        )
}