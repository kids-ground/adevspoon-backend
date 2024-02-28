package com.adevspoon.api.auth.service

import com.adevspoon.api.common.dto.JwtTokenInfo
import com.adevspoon.api.common.dto.JwtTokenType
import com.adevspoon.api.common.utils.JwtProcessor
import com.adevspoon.api.member.dto.request.SocialLoginRequest
import com.adevspoon.api.member.dto.response.ServiceToken
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
    private val oAuthAdapter: OAuthAdapter,
    private val jwtProcessor: JwtProcessor,
) {
    fun signIn(loginRequest: SocialLoginRequest): SocialLoginResponse {
        val oAuthUserInfo = getOAuthUserInfo(loginRequest)
        val userInfo = memberService.getOrCreateUser(oAuthUserInfo, loginRequest.loginType.toUserOAuth())
        val jwtToken = getServiceToken(userInfo.userId)

        memberService.setRefreshToken(userInfo.userId, jwtToken.refreshToken)

        userInfo.token = jwtToken
        return userInfo
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

    private fun getServiceToken(userId: Long): ServiceToken = ServiceToken(
        accessToken = jwtProcessor.createToken(JwtTokenInfo(JwtTokenType.ACCESS, userId)),
        refreshToken = jwtProcessor.createToken(JwtTokenInfo(JwtTokenType.REFRESH, userId))
    )
}