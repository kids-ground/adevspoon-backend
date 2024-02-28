package com.adevspoon.api.member.service

import com.adevspoon.api.common.properties.ImageProperties
import com.adevspoon.api.common.utils.NicknameProcessor
import com.adevspoon.api.member.dto.response.SocialLoginResponse
import com.adevspoon.domain.member.adapter.MemberActivityDomainAdapter
import com.adevspoon.domain.member.adapter.MemberDomainAdapter
import com.adevspoon.domain.member.domain.User
import com.adevspoon.domain.member.domain.UserActivity
import com.adevspoon.domain.member.domain.enums.UserOAuth
import com.adevspoon.infrastructure.oauth.dto.OAuthUserInfoResponse
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional

@Service
class MemberService(
    private val memberDomainAdapter: MemberDomainAdapter,
    private val memberActivityDomainAdapter: MemberActivityDomainAdapter,
    private val imageProperties: ImageProperties,
    private val nicknameProcessor: NicknameProcessor,
) {
    @Transactional
    fun getOrCreateUser(oauthUserInfo: OAuthUserInfoResponse, oauthType: UserOAuth): SocialLoginResponse =
        when (oauthType) {
            UserOAuth.kakao -> memberDomainAdapter.findByOAuthAndAppleId(oauthType, oauthUserInfo.id)
            UserOAuth.apple -> memberDomainAdapter.findByOAuthAndKakaoId(oauthType, oauthUserInfo.id.toLong())
        }?.let {
            SocialLoginResponse.from(it, false)
        } ?: createUser(oauthUserInfo, oauthType)

    @Transactional
    fun setRefreshToken(userId: Long, reference: String) {
        val user = memberDomainAdapter.findByUserId(userId)
        user?.refreshToken = reference
    }

    private fun createUser(oauthUserInfo: OAuthUserInfoResponse, oauthType: UserOAuth): SocialLoginResponse {
        val user = when (oauthType) {
            UserOAuth.kakao -> User(oAuth = oauthType, kakaoId = oauthUserInfo.id.toLong())
            UserOAuth.apple -> User(oAuth = oauthType, appleId = oauthUserInfo.id)
        }.apply {
            profileImg = oauthUserInfo.profileImageUrl ?: imageProperties.profileUrl
            thumbnailImg = oauthUserInfo.thumbnailImageUrl ?: imageProperties.thumbnailUrl
            nickname = nicknameProcessor.createRandomNickname()
        }

        memberDomainAdapter.save(user)

        val userActivity = UserActivity(id = user.id)
        memberActivityDomainAdapter.save(userActivity)

        return SocialLoginResponse.from(user, true)
    }
}