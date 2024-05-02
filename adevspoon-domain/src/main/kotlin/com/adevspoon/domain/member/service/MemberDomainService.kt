package com.adevspoon.domain.member.service

import com.adevspoon.common.dto.OAuthUserInfo
import com.adevspoon.domain.common.annotation.DomainService
import com.adevspoon.domain.common.repository.LikeRepository
import com.adevspoon.domain.member.domain.UserActivityEntity
import com.adevspoon.domain.member.domain.UserEntity
import com.adevspoon.domain.member.domain.enums.UserOAuth
import com.adevspoon.domain.member.dto.request.GetLikeList
import com.adevspoon.domain.member.dto.request.MemberUpdateRequireDto
import com.adevspoon.domain.member.dto.response.LikeInfo
import com.adevspoon.domain.member.dto.response.LikeListInfo
import com.adevspoon.domain.member.dto.response.MemberAndSignup
import com.adevspoon.domain.member.dto.response.MemberProfile
import com.adevspoon.domain.member.exception.MemberBadgeNotFoundException
import com.adevspoon.domain.member.exception.MemberNotFoundException
import com.adevspoon.domain.member.repository.BadgeRepository
import com.adevspoon.domain.member.repository.UserActivityRepository
import com.adevspoon.domain.member.repository.UserBadgeAchieveRepository
import com.adevspoon.domain.member.repository.UserRepository
import com.adevspoon.domain.techQuestion.exception.QuestionAnswerNotFoundException
import com.adevspoon.domain.techQuestion.service.QuestionDomainService
import org.slf4j.LoggerFactory
import org.springframework.beans.factory.annotation.Value
import org.springframework.data.repository.findByIdOrNull
import org.springframework.transaction.annotation.Transactional
import java.time.LocalDate

@DomainService
class MemberDomainService(
    private val userRepository: UserRepository,
    private val userBadgeAchieveRepository: UserBadgeAchieveRepository,
    private val userActivityRepository: UserActivityRepository,
    private val badgeRepository: BadgeRepository,
    private val likeRepository: LikeRepository,
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
        val user = getUserEntity(userId)
        val userBadgeList = userBadgeAchieveRepository.findUserBadgeList(userId)
        val userRepresentativeBadge = userBadgeList.firstOrNull {
            it.id == user.representativeBadge
        }

        return MemberProfile.from(user, userBadgeList, userRepresentativeBadge)
    }

    @Transactional
    fun getOtherMemberProfile(userId: Long): MemberProfile {
        val user = getUserEntity(userId)
        val userRepresentativeBadge = user.representativeBadge
            ?.let {
                badgeRepository.findByIdOrNull(user.representativeBadge)
                    ?: throw MemberBadgeNotFoundException()
            }

        return MemberProfile.from(user, null, userRepresentativeBadge)
    }

    @Transactional
    fun updateMemberProfile(updateInfo: MemberUpdateRequireDto): MemberProfile {
        val user = getUserEntity(updateInfo.memberId)
        val userBadgeList = userBadgeAchieveRepository.findUserBadgeList(updateInfo.memberId)
        var userRepresentativeBadge = userBadgeList.firstOrNull {
            it.id == user.representativeBadge
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
        val user = getUserEntity(userId)
        user.apply {
            this.refreshToken = refreshToken
        }
    }

    @Transactional(readOnly = true)
    fun getLikeList(request: GetLikeList): LikeListInfo {
        val likeList = likeRepository.findAnswerLikeList(request.requestMemberId, request.startId, request.take)
        val badgeList = badgeRepository.findAll()

        return LikeListInfo(
            list = likeList.content.map { like ->
                if (like.answer == null) throw QuestionAnswerNotFoundException()

                LikeInfo(
                    id = like.id,
                    postType = "answer",
                    postId = like.answer!!.id,
                    title = like.answer!!.question.question,
                    content = like.answer!!.answer ?: "",
                    date = like.createdAt?.toLocalDate() ?: LocalDate.now(),
                    writer = MemberProfile.from(
                        like.answer!!.user,
                        null,
                        badgeList.find { it.id == like.answer!!.user.representativeBadge }
                    ),
                    isLiked = true,
                    likeCount = like.answer?.likeCnt ?: 0,
                )
            },
            nextStartId = likeList.lastOrNull()?.id,
        )
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

    private fun getUserEntity(userId: Long): UserEntity {
        return userRepository.findByIdOrNull(userId) ?: throw MemberNotFoundException()
    }
}