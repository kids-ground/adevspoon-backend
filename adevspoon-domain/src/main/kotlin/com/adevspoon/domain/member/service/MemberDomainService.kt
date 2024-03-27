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
import com.adevspoon.domain.member.repository.UserBadgeAchieveRepository
import com.adevspoon.domain.member.repository.UserRepository
import com.adevspoon.domain.techQuestion.service.QuestionDomainService
import org.slf4j.LoggerFactory
import org.springframework.beans.factory.annotation.Value
import org.springframework.data.repository.findByIdOrNull
import org.springframework.transaction.annotation.Transactional

@DomainService
class MemberDomainService(
    private val userRepository: UserRepository,
    private val userBadgeAchieveRepository: UserBadgeAchieveRepository,
    private val userActivityRepository: UserActivityRepository,
    private val questionDomainService: QuestionDomainService,
    private val nicknameDomainService: NicknameDomainService,
) {
    @Value("\${default.profile-url}")
    private lateinit var defaultProfileImg: String
    @Value("\${default.thumbnail-url}")
    private lateinit var defaultThumbnailImg: String
    private val logger = LoggerFactory.getLogger(this.javaClass)!!

    @Transactional
    fun getMemberAndSignUp(oauthInfo: OAuthUserInfo): MemberAndSignup {
        val oauthType = UserOAuth.from(oauthInfo.type)
        when (oauthType) {
            UserOAuth.KAKAO -> userRepository.findByOAuthAndKakaoId(oauthType, oauthInfo.id.toLong())
            UserOAuth.APPLE -> userRepository.findByOAuthAndAppleId(oauthType, oauthInfo.id)
        }?.let {
            return MemberAndSignup.from(it)
        }

        return createMember(oauthInfo)
    }

    @Transactional
    fun getMemberProfile(userId: Long): MemberProfile {
        val user = userRepository.findByIdOrNull(userId) ?: throw MemberNotFoundException()
        val userBadgeList = userBadgeAchieveRepository.findUserBadgeList(userId)
        val userRepresentativeBadge = userBadgeList.firstOrNull {
            it.id?.equals(user.representativeBadge) ?: false
        }

        return MemberProfile.from(user, userBadgeList, userRepresentativeBadge)
    }

    @Transactional
    fun updateMemberProfile(updateInfo: MemberUpdateRequireDto): MemberProfile {
        val user = userRepository.findByIdOrNull(updateInfo.memberId) ?: throw MemberBadgeNotFoundException()
        logger.warn("유정정보 확인 : ${user.oAuth}")
        val userBadgeList = userBadgeAchieveRepository.findUserBadgeList(updateInfo.memberId)
        var userRepresentativeBadge = userBadgeList.firstOrNull {
            it.id?.equals(user.representativeBadge) ?: false
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
            this.representativeBadge = userRepresentativeBadge?.id
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
            UserOAuth.KAKAO -> UserEntity(oAuth = oauthType, kakaoId = oauthInfo.id.toLong())
            UserOAuth.APPLE -> UserEntity(oAuth = oauthType, appleId = oauthInfo.id)
        }.apply {
            email = oauthInfo.email ?: ""
            profileImg = oauthInfo.profileImageUrl ?: defaultProfileImg
            thumbnailImg = oauthInfo.thumbnailImageUrl ?: defaultThumbnailImg
            nickname = nicknameDomainService.createRandomNickname()
        }.also {
            userRepository.save(it)
            userActivityRepository.save(UserActivityEntity(id = it.id))
        }.let {
            return MemberAndSignup.from(it, true)
        }
    }
}