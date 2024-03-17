package com.adevspoon.domain.member.service

import com.adevspoon.domain.common.annotation.DomainService
import com.adevspoon.domain.member.domain.UserEntity
import com.adevspoon.domain.member.domain.UserActivityEntity
import com.adevspoon.domain.member.domain.enums.UserOAuth
import com.adevspoon.domain.member.dto.request.MemberUpdateRequestDto
import com.adevspoon.domain.member.dto.response.MemberProfileResponseDto
import com.adevspoon.domain.member.exception.MemberDomainErrorCode
import com.adevspoon.domain.member.repository.UserActivityRepository
import com.adevspoon.domain.member.repository.UserBadgeAcheiveRepository
import com.adevspoon.domain.member.repository.UserRepository
import com.adevspoon.domain.techQuestion.service.QuestionDomainService
import org.springframework.data.repository.findByIdOrNull
import org.springframework.transaction.annotation.Transactional

@DomainService
class MemberDomainService(
    private val userRepository: UserRepository,
    private val userBadgeAcheiveRepository: UserBadgeAcheiveRepository,
    private val userActivityRepository: UserActivityRepository,
    private val questionDomainService: QuestionDomainService,
) {
    // 유저정보와 회원가입 여부반환
    @Transactional
    fun getMemberAndIsSignUp(oAuth: UserOAuth, oAuthId: String): Pair<UserEntity, Boolean> {
        val user = when (oAuth) {
            UserOAuth.kakao -> userRepository.findByOAuthAndKakaoId(oAuth, oAuthId.toLong())
            UserOAuth.apple -> userRepository.findByOAuthAndAppleId(oAuth, oAuthId)
        }
        if (user != null) return user to false

        return when (oAuth) {
            UserOAuth.kakao -> UserEntity(oAuth = oAuth, kakaoId = oAuthId.toLong())
            UserOAuth.apple -> UserEntity(oAuth = oAuth, appleId = oAuthId)
        }.also {
            userRepository.save(it)
            userActivityRepository.save(UserActivityEntity(id = it.id))
        } to true
    }

    @Transactional
    fun getMemberProfile(userId: Long): MemberProfileResponseDto {
        val user = userRepository.findByIdOrNull(userId) ?: throw MemberDomainErrorCode.USER_NOT_FOUND.getException()
        val userBadgeList = userBadgeAcheiveRepository.findUserBadgeList(userId)
        val userRepresentativeBadge = userBadgeList.firstOrNull {
            it.id?.toString()?.equals(user.representativeBadge) ?: false
        }

        return MemberProfileResponseDto.from(user, userBadgeList, userRepresentativeBadge)
    }

    @Transactional
    fun updateMemberProfile(userId: Long, updateInfo: MemberUpdateRequestDto): MemberProfileResponseDto {
        val user = userRepository.findByIdOrNull(userId) ?: throw MemberDomainErrorCode.USER_NOT_FOUND.getException()
        val userBadgeList = userBadgeAcheiveRepository.findUserBadgeList(userId)
        var userRepresentativeBadge = userBadgeList.firstOrNull {
            it.id?.toString()?.equals(user.representativeBadge) ?: false
        }

        if (!updateInfo.categoryIds.isNullOrEmpty())
            questionDomainService.updateQuestionCategory(user, updateInfo.categoryIds)
        if (updateInfo.representativeBadge != null)
            userRepresentativeBadge = userBadgeList
                .firstOrNull { it.id == updateInfo.representativeBadge }
                ?: throw MemberDomainErrorCode.USER_BADGE_NOT_FOUND.getException()

        user.apply {
            this.nickname = updateInfo.nickname ?: user.nickname
            this.fcmToken = updateInfo.fcmToken ?: user.fcmToken
            this.profileImg = updateInfo.profileImageUrl ?: user.profileImg
            this.thumbnailImg = updateInfo.thumbnailImageUrl ?: user.thumbnailImg
            this.representativeBadge = userRepresentativeBadge?.id?.toString()
        }

        return MemberProfileResponseDto.from(user, userBadgeList, userRepresentativeBadge)
    }
}