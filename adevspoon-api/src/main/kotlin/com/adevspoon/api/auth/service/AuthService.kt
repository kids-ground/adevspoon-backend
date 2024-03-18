package com.adevspoon.api.auth.service

import com.adevspoon.api.common.annotation.ApplicationService
import com.adevspoon.api.common.dto.JwtTokenInfo
import com.adevspoon.api.common.dto.JwtTokenType
import com.adevspoon.api.common.properties.ImageProperties
import com.adevspoon.api.common.util.JwtProcessor
import com.adevspoon.api.member.dto.request.SocialLoginRequest
import com.adevspoon.api.member.dto.response.TokenResponse
import com.adevspoon.api.member.dto.response.MemberAndTokenResponse
import com.adevspoon.domain.member.service.MemberDomainService
import com.adevspoon.infrastructure.oauth.service.OAuthAdapter

@ApplicationService
class AuthService(
    private val memberDomainService: MemberDomainService,
    private val oAuthAdapter: OAuthAdapter,
    private val jwtProcessor: JwtProcessor,
) {
    fun signIn(loginRequest: SocialLoginRequest): MemberAndTokenResponse {
        val oAuthUserInfo = oAuthAdapter.getOAuthUserInfo(loginRequest.toOAuthUserInfoRequest())
        val memberAndSignup = memberDomainService.getMemberAndSignUp(oAuthUserInfo)
        val tokenResponse = getServiceToken(memberAndSignup.memberId)

        return MemberAndTokenResponse.from(memberAndSignup, tokenResponse)
    }

    fun refreshToken(userId: Long): TokenResponse = getServiceToken(userId)

    private fun getServiceToken(userId: Long): TokenResponse =
        TokenResponse(
            accessToken = jwtProcessor.createToken(JwtTokenInfo(JwtTokenType.ACCESS, userId)),
            refreshToken = jwtProcessor.createToken(JwtTokenInfo(JwtTokenType.REFRESH, userId)),
        ).also {
            memberDomainService.updateMemberToken(userId, it.refreshToken)
        }
}