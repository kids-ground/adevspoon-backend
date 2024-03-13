package com.adevspoon.api.auth.service

import com.adevspoon.api.common.dto.JwtTokenInfo
import com.adevspoon.api.common.dto.JwtTokenType
import com.adevspoon.api.common.properties.ImageProperties
import com.adevspoon.api.common.util.JwtProcessor
import com.adevspoon.api.common.util.NicknameProcessor
import com.adevspoon.api.member.dto.request.SocialLoginRequest
import com.adevspoon.api.member.dto.response.ServiceToken
import com.adevspoon.api.member.dto.response.SocialLoginResponse
import com.adevspoon.domain.member.domain.enums.UserOAuth
import com.adevspoon.domain.member.service.MemberDomainService
import com.adevspoon.infrastructure.oauth.service.OAuthAdapter
import org.springframework.stereotype.Service

@Service
class AuthService(
    private val memberDomainService: MemberDomainService,
    private val oAuthAdapter: OAuthAdapter,
    private val jwtProcessor: JwtProcessor,
    private val imageProperties: ImageProperties,
    private val nicknameProcessor: NicknameProcessor,
) {
    fun signIn(loginRequest: SocialLoginRequest): SocialLoginResponse {
        val oAuthUserInfo = oAuthAdapter.getOAuthUserInfo(loginRequest.toOAuthUserInfoRequest())
        val (user, isSignUp) = memberDomainService.getMemberAndIsSignUp(
            enumValueOf<UserOAuth>(loginRequest.loginType.name.lowercase()),
            oAuthUserInfo.id,
        )

        if (isSignUp) user.apply {
            email = oAuthUserInfo.email ?: ""
            profileImg = oAuthUserInfo.profileImageUrl ?: imageProperties.profileUrl
            thumbnailImg = oAuthUserInfo.thumbnailImageUrl ?: imageProperties.thumbnailUrl
            nickname = nicknameProcessor.createRandomNickname()
        }

        val jwtServiceToken = ServiceToken(
            accessToken = jwtProcessor.createToken(JwtTokenInfo(JwtTokenType.ACCESS, user.id)),
            refreshToken = jwtProcessor.createToken(JwtTokenInfo(JwtTokenType.REFRESH, user.id)),
        ).also { user.refreshToken = it.refreshToken }

        return SocialLoginResponse.from(user, isSignUp)
            .apply { token = jwtServiceToken }
    }
}