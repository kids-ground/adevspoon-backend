package com.adevspoon.api.member.dto.request

import com.adevspoon.common.enums.SocialType
import com.adevspoon.infrastructure.oauth.dto.OAuthUserInfoRequest
import io.swagger.v3.oas.annotations.media.Schema

// TODO: Converter로 Enum값 value로 바꿀 필요있음
data class SocialLoginRequest(
    @Schema(description = "소셜 로그인 토큰 - 카카오면 accessToken, 애플은 identityToken")
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