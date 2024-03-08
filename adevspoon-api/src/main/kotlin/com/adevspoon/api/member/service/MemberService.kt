package com.adevspoon.api.member.service

import com.adevspoon.api.common.properties.ImageProperties
import com.adevspoon.api.common.util.NicknameProcessor
import com.adevspoon.api.member.dto.response.SocialLoginResponse
import com.adevspoon.domain.member.adapter.MemberActivityDomainAdapter
import com.adevspoon.domain.member.adapter.MemberDomainAdapter
import com.adevspoon.domain.member.domain.User
import com.adevspoon.domain.member.domain.UserActivity
import com.adevspoon.domain.member.domain.enums.UserOAuth
import com.adevspoon.infrastructure.oauth.dto.OAuthUserInfoResponse
import org.slf4j.LoggerFactory
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional

@Service
class MemberService(
    private val memberDomainAdapter: MemberDomainAdapter,
    private val memberActivityDomainAdapter: MemberActivityDomainAdapter,
    private val imageProperties: ImageProperties,
    private val nicknameProcessor: NicknameProcessor,
) {
    private val log = LoggerFactory.getLogger(this.javaClass)!!

    @Transactional
    fun getOrCreateUser(oauthUserInfo: OAuthUserInfoResponse, oauthType: UserOAuth): SocialLoginResponse =
        when (oauthType) {
            UserOAuth.kakao -> memberDomainAdapter.findByOAuthAndKakaoId(oauthType, oauthUserInfo.id.toLong())
            UserOAuth.apple -> memberDomainAdapter.findByOAuthAndAppleId(oauthType, oauthUserInfo.id)
        }?.let {
            SocialLoginResponse.from(it, false)
        } ?: createUser(oauthUserInfo, oauthType)

    @Transactional
    fun setRefreshToken(userId: Long, reference: String) {
        val user = memberDomainAdapter.findByUserId(userId)
        user?.refreshToken = reference
    }

    private fun createUser(oauthUserInfo: OAuthUserInfoResponse, oauthType: UserOAuth): SocialLoginResponse {
        log.info("유저 생성 $oauthType ${oauthUserInfo.id}")
        val user = when (oauthType) {
            UserOAuth.kakao -> User(oAuth = oauthType, kakaoId = oauthUserInfo.id.toLong())
            UserOAuth.apple -> User(oAuth = oauthType, appleId = oauthUserInfo.id)
        }.apply {
            email = oauthUserInfo.email ?: ""
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