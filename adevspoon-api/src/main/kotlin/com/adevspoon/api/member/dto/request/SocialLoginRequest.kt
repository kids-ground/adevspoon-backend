package com.adevspoon.api.member.dto.request

import com.adevspoon.common.enums.SocialType
import com.adevspoon.infrastructure.oauth.dto.OAuthUserInfoRequest
import io.swagger.v3.oas.annotations.media.Schema

data class SocialLoginRequest(
    @Schema(description = "소셜 로그인 토큰 - 카카오면 accessToken, 애플은 identityToken")
    val oauthToken: String,
    val loginType: LoginType,
) {
    fun toOAuthUserInfoRequest(): OAuthUserInfoRequest {
        val socialType = loginType.toSocialType()
        return OAuthUserInfoRequest(
            type = socialType,
            kakaoAccessToken = if (socialType == SocialType.KAKAO) oauthToken else null,
            appleIdentityToken = if (socialType == SocialType.APPLE) oauthToken else null,
        )
    }
}