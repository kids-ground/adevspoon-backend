package com.adevspoon.domain.member.dto.response

import com.adevspoon.domain.member.domain.Badge
import com.adevspoon.domain.member.domain.User
import java.time.LocalDateTime

data class MemberProfileResponseDto(
    val memberId: Long,
    val nickname: String,
    val statusMessage: String,
    val profileImageUrl: String,
    val thumbnailImageUrl: String,
    val questionCnt: Int,
    val answerCnt: Int,
    val createdAt: LocalDateTime,
    val updatedAt: LocalDateTime,
    val alarmOn: Boolean,
    val badges: List<BadgeResponseDto>?,
    val profileBelt: String,
    val representativeBadge: BadgeResponseDto?,
) {
    companion object {
        fun from(user: User, hasBadgeList: List<Badge>, representativeBadge: Badge?) = MemberProfileResponseDto(
            memberId = user.id,
            nickname = user.nickname ?: "",
            statusMessage = user.statusMessage ?: "",
            profileImageUrl = user.profileImg ?: "",
            thumbnailImageUrl = user.thumbnailImg ?: "",
            questionCnt = user.questionCnt,
            answerCnt = user.answerCnt,
            createdAt = user.createdAt ?: LocalDateTime.now(),
            updatedAt = user.updatedAt ?: LocalDateTime.now(),
            alarmOn = user.fcmToken != null,
            profileBelt = user.profileBelt.name.lowercase(),
            badges = hasBadgeList.map { BadgeResponseDto.from(it) },
            representativeBadge = representativeBadge?.let { BadgeResponseDto.from(it) }
        )
    }
}