package com.adevspoon.api.auth.service

import com.adevspoon.api.auth.dto.request.RefreshTokenRequest
import com.adevspoon.api.common.annotation.ApplicationService
import com.adevspoon.api.common.dto.JwtTokenInfo
import com.adevspoon.api.common.dto.JwtTokenType
import com.adevspoon.api.common.util.JwtProcessor
import com.adevspoon.api.member.dto.request.SocialLoginRequest
import com.adevspoon.api.auth.dto.response.TokenResponse
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
        val tokenResponse = getNewServiceToken(memberAndSignup.memberId)

        memberDomainService.checkAndUpdateToken(memberAndSignup.memberId, null, tokenResponse.refreshToken)

        return MemberAndTokenResponse.from(memberAndSignup, tokenResponse)
    }

    fun signOut(memberId: Long): String {
        memberDomainService.signOut(memberId)
        return "로그아웃 되었습니다"
    }

    fun withdraw(memberId: Long): String {
        memberDomainService.withdraw(memberId)
        return "회원탈퇴 완료되었습니다"
    }

    fun refreshToken(request: RefreshTokenRequest): TokenResponse {
        jwtProcessor.checkOwnToken(request.accessToken)
        val oldTokenInfo = jwtProcessor.validateServiceToken(request.refreshToken)

        return getNewServiceToken(oldTokenInfo.userId)
            .also {
                memberDomainService.checkAndUpdateToken(oldTokenInfo.userId, request.refreshToken, it.refreshToken)
            }
    }

    private fun getNewServiceToken(userId: Long): TokenResponse =
        TokenResponse(
            accessToken = jwtProcessor.createToken(JwtTokenInfo(JwtTokenType.ACCESS, userId)),
            refreshToken = jwtProcessor.createToken(JwtTokenInfo(JwtTokenType.REFRESH, userId)),
        )
}