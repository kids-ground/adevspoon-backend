package com.adevspoon.domain.member.service

import com.adevspoon.common.dto.OAuthUserInfo
import com.adevspoon.domain.common.annotation.ActivityEvent
import com.adevspoon.domain.common.annotation.ActivityEventType
import com.adevspoon.domain.common.annotation.DomainService
import com.adevspoon.domain.common.repository.LikeRepository
import com.adevspoon.domain.member.domain.AttendanceEntity
import com.adevspoon.domain.member.domain.AttendanceId
import com.adevspoon.domain.member.domain.UserActivityEntity
import com.adevspoon.domain.member.domain.UserEntity
import com.adevspoon.domain.member.domain.enums.UserOAuth
import com.adevspoon.domain.member.domain.enums.UserStatus
import com.adevspoon.domain.member.dto.request.GetActivityRequest
import com.adevspoon.domain.member.dto.request.GetLikeList
import com.adevspoon.domain.member.dto.request.MemberUpdateRequireDto
import com.adevspoon.domain.member.dto.response.*
import com.adevspoon.domain.member.exception.MemberAlreadyExpiredRefreshTokenException
import com.adevspoon.domain.member.exception.MemberBadgeNotFoundException
import com.adevspoon.domain.member.exception.MemberNotFoundException
import com.adevspoon.domain.member.repository.*
import com.adevspoon.domain.techQuestion.exception.QuestionAnswerNotFoundException
import com.adevspoon.domain.techQuestion.repository.AnswerRepository
import com.adevspoon.domain.techQuestion.service.QuestionDomainService
import org.slf4j.LoggerFactory
import org.springframework.beans.factory.annotation.Value
import org.springframework.dao.DataIntegrityViolationException
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
    private val attendanceRepository: AttendanceRepository,
    private val answerRepository: AnswerRepository,
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
    fun getMemberProfile(memberId: Long): MemberProfile {
        val user = getUserEntity(memberId)
        val userBadgeList = userBadgeAchieveRepository.findUserBadgeList(memberId)
        val userRepresentativeBadge = userBadgeList.firstOrNull {
            it.id == user.representativeBadge
        }

        return MemberProfile.from(user, userBadgeList, userRepresentativeBadge)
    }

    @Transactional
    fun getOtherMemberProfile(memberId: Long): MemberProfile {
        val user = getUserEntity(memberId)
        val userRepresentativeBadge = user.representativeBadge
            ?.let {
                badgeRepository.findByIdOrNull(user.representativeBadge)
                    ?: throw MemberBadgeNotFoundException()
            }

        return MemberProfile.from(user, null, userRepresentativeBadge)
    }

    @ActivityEvent(type = ActivityEventType.ATTENDANCE)
    @Transactional
    fun attend(memberId: Long): MemberProfile {
        val member = getUserEntity(memberId)
        return try {
            attendanceRepository.save(AttendanceEntity(AttendanceId(member, LocalDate.now().atStartOfDay())))
            getMemberProfile(memberId)
        } catch (e: DataIntegrityViolationException) {
            getMemberProfile(memberId)
        } catch (e: Exception) {
            throw e
        }
    }

    @Transactional
    fun signOut(memberId: Long) {
        val member = getUserEntity(memberId)
        member.signOut()
    }

    @Transactional
    fun withdraw(memberId: Long) {
        val member = getUserEntity(memberId)
        if (member.status == UserStatus.EXIT) throw MemberNotFoundException()
        member.withdraw()
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
    fun checkAndUpdateToken(userId: Long, oldToken: String?, newToken: String) {
        val user = getUserEntity(userId)

        if (oldToken != null && user.refreshToken != oldToken) throw MemberAlreadyExpiredRefreshTokenException()

        user.apply { refreshToken = newToken }
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

    @Transactional(readOnly = true)
    fun getBadgeListWithAchievedInfo(memberId: Long): List<BadgeAchievedInfo> {
        val member = getUserEntity(memberId)
        val activity = userActivityRepository.findByIdOrNull(memberId) ?: throw MemberNotFoundException()
        val memberBadgeList = userBadgeAchieveRepository.findUserBadgeList(memberId)

        return badgeRepository.findAll()
            .map { badge ->
                BadgeAchievedInfo.from(
                    badge = badge,
                    isAchieved = memberBadgeList.any { badge.id == it.id },
                    isRepresentative = member.representativeBadge == badge.id,
                    userValue = activity.fieldValue(badge::criteria.name),
                )
            }
    }

    @Transactional(readOnly = true)
    fun getMonthlyAnswerActivity(request: GetActivityRequest): List<AnswerActivityInfo> {
        val member = getUserEntity(request.userId)
        return answerRepository.findAnswerCountsByMonthAndUser(request.year, request.month, member)
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