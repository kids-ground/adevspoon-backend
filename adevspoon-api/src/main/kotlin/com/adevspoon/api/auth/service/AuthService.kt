package com.adevspoon.api.auth.service

import com.adevspoon.api.member.dto.request.SocialLoginRequest
import com.adevspoon.api.member.dto.response.SocialLoginResponse
import com.adevspoon.api.member.service.MemberService
import com.adevspoon.infrastructure.oauth.dto.OAuthType
import com.adevspoon.infrastructure.oauth.dto.OAuthUserInfoRequest
import com.adevspoon.infrastructure.oauth.dto.OAuthUserInfoResponse
import com.adevspoon.infrastructure.oauth.service.OAuthAdapter
import org.springframework.stereotype.Service

@Service
class AuthService(
    private val memberService: MemberService,
    private val oAuthAdapter: OAuthAdapter
) {

    fun signIn(loginRequest: SocialLoginRequest): SocialLoginResponse {
        val oAuthUserInfo = getOAuthUserInfo(loginRequest)
        val socialLoginResponse = memberService.getOrCreateUser(oAuthUserInfo.id, loginRequest.loginType.toUserOAuth())
        // TODO: JWT 토큰 생성하기
        return socialLoginResponse
    }

    private fun getOAuthUserInfo(loginRequest: SocialLoginRequest): OAuthUserInfoResponse {
        val oAuthType = loginRequest.loginType.toOAuthType()
        val oAuthUserInfoRequest = OAuthUserInfoRequest(
            type = oAuthType,
            kakaoAccessToken = if (oAuthType == OAuthType.KAKAO) loginRequest.oauthToken else null,
            appleIdentityToken = if (oAuthType == OAuthType.APPLE) loginRequest.oauthToken else null,
        )
        return oAuthAdapter.getOAuthUserInfo(oAuthUserInfoRequest)
    }
}