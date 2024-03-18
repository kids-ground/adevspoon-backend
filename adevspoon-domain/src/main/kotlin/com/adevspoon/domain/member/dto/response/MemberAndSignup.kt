package com.adevspoon.domain.member.dto.response

import com.adevspoon.domain.member.domain.UserEntity
import java.time.LocalDateTime

data class MemberAndSignup(
    val isSign: Boolean,
    val memberId: Long,
    val nickname: String,
    val profileImg: String?,
    val thumbnailImg: String?,
    val questionCnt: Int,
    val answerCnt: Int,
    val alarmOn: Boolean,
    val createdAt: LocalDateTime,
    val updatedAt: LocalDateTime,
) {
    companion object {
        fun from(user: UserEntity, isSignup: Boolean = false) =
            MemberAndSignup(
                isSign = isSignup,
                memberId = user.id,
                nickname = user.nickname ?: "",
                profileImg = user.profileImg,
                thumbnailImg = user.thumbnailImg,
                questionCnt = user.questionCnt,
                answerCnt = user.answerCnt,
                alarmOn = user.fcmToken != null,
                createdAt = user.createdAt ?: LocalDateTime.now(),
                updatedAt = user.updatedAt ?: LocalDateTime.now(),
            )

    }
}
