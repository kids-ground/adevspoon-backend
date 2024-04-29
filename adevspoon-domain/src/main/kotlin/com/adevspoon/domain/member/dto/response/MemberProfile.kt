package com.adevspoon.domain.member.dto.response

import com.adevspoon.domain.member.domain.BadgeEntity
import com.adevspoon.domain.member.domain.UserEntity
import java.time.LocalDateTime

data class MemberProfile(
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
    val badges: List<Badge>?,
    val profileBelt: String,
    val representativeBadge: Badge?,
) {
    companion object {
        fun from(user: UserEntity, hasBadgeList: List<BadgeEntity>?, representativeBadge: BadgeEntity?) = MemberProfile(
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
            badges = hasBadgeList?.map { Badge.from(it) },
            representativeBadge = representativeBadge?.let { Badge.from(it) }
        )
    }
}