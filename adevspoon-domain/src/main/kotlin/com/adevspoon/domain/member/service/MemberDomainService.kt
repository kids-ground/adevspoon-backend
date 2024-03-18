package com.adevspoon.domain.member.service

import com.adevspoon.common.dto.OAuthUserInfo
import com.adevspoon.domain.common.annotation.DomainService
import com.adevspoon.domain.member.domain.UserEntity
import com.adevspoon.domain.member.domain.UserActivityEntity
import com.adevspoon.domain.member.domain.enums.UserOAuth
import com.adevspoon.domain.member.dto.request.MemberUpdateRequireDto
import com.adevspoon.domain.member.dto.response.MemberAndSignup
import com.adevspoon.domain.member.dto.response.MemberProfile
import com.adevspoon.domain.member.exception.MemberBadgeNotFoundException
import com.adevspoon.domain.member.exception.MemberNotFoundException
import com.adevspoon.domain.member.repository.UserActivityRepository
import com.adevspoon.domain.member.repository.UserBadgeAcheiveRepository
import com.adevspoon.domain.member.repository.UserRepository
import com.adevspoon.domain.techQuestion.service.QuestionDomainService
import org.springframework.beans.factory.annotation.Value
import org.springframework.data.repository.findByIdOrNull
import org.springframework.transaction.annotation.Transactional

@DomainService
class MemberDomainService(
    private val userRepository: UserRepository,
    private val userBadgeAcheiveRepository: UserBadgeAcheiveRepository,
    private val userActivityRepository: UserActivityRepository,
    private val questionDomainService: QuestionDomainService,
    private val nicknameDomainService: NicknameDomainService,
) {
    @Value("\${default.profile-url}")
    private lateinit var defaultProfileImg: String
    @Value("\${default.thumbnail-url}")
    private lateinit var defaultThumbnailImg: String

    @Transactional
    fun getMemberAndSignUp(oauthInfo: OAuthUserInfo): MemberAndSignup {
        val oauthType = UserOAuth.from(oauthInfo.type)
        when (oauthType) {
            UserOAuth.kakao -> userRepository.findByOAuthAndKakaoId(oauthType, oauthInfo.id.toLong())
            UserOAuth.apple -> userRepository.findByOAuthAndAppleId(oauthType, oauthInfo.id)
        }?.let {
            return MemberAndSignup.from(it)
        }

        return createMember(oauthInfo)
    }

    @Transactional
    fun getMemberProfile(userId: Long): MemberProfile {
        val user = userRepository.findByIdOrNull(userId) ?: throw MemberNotFoundException()
        val userBadgeList = userBadgeAcheiveRepository.findUserBadgeList(userId)
        val userRepresentativeBadge = userBadgeList.firstOrNull {
            it.id?.toString()?.equals(user.representativeBadge) ?: false
        }

        return MemberProfile.from(user, userBadgeList, userRepresentativeBadge)
    }

    @Transactional
    fun updateMemberProfile(updateInfo: MemberUpdateRequireDto): MemberProfile {
        val user = userRepository.findByIdOrNull(updateInfo.memberId) ?: throw MemberBadgeNotFoundException()
        val userBadgeList = userBadgeAcheiveRepository.findUserBadgeList(updateInfo.memberId)
        var userRepresentativeBadge = userBadgeList.firstOrNull {
            it.id?.toString()?.equals(user.representativeBadge) ?: false
        }

        if (!updateInfo.categoryIds.isNullOrEmpty())
            questionDomainService.updateQuestionCategory(user, updateInfo.categoryIds)
        if (updateInfo.representativeBadge != null)
            userRepresentativeBadge = userBadgeList
                .firstOrNull { it.id == updateInfo.representativeBadge }
                ?: throw MemberBadgeNotFoundException()

        user.apply {
            updateInfo.nickname?.let { this.nickname = it }
            updateInfo.fcmToken?.let { this.fcmToken = it }
            updateInfo.profileImageUrl?.let { this.profileImg = it }
            updateInfo.thumbnailImageUrl?.let { this.thumbnailImg = it }
            this.representativeBadge = userRepresentativeBadge?.id?.toString()
        }

        return MemberProfile.from(user, userBadgeList, userRepresentativeBadge)
    }

    @Transactional
    fun updateMemberToken(userId: Long, refreshToken: String) {
        val user = userRepository.findByIdOrNull(userId) ?: throw MemberNotFoundException()
        user.apply {
            this.refreshToken = refreshToken
        }
    }

    private fun createMember(oauthInfo: OAuthUserInfo): MemberAndSignup {
        val oauthType = UserOAuth.from(oauthInfo.type)
        when (oauthType) {
            UserOAuth.kakao -> UserEntity(oAuth = oauthType, kakaoId = oauthInfo.id.toLong())
            UserOAuth.apple -> UserEntity(oAuth = oauthType, appleId = oauthInfo.id)
        }.apply {
            email = oauthInfo.email ?: ""
            profileImg = oauthInfo.profileImageUrl ?: defaultProfileImg
            thumbnailImg = oauthInfo.thumbnailImageUrl ?: defaultThumbnailImg
            nickname = nicknameDomainService.createRandomNickname()
        }.also {
            userRepository.save(it)
            userActivityRepository.save(UserActivityEntity(id = it.id))
        }.let {
            return MemberAndSignup.from(it)
        }
    }
}