package com.adevspoon.api.member.dto.response

import com.adevspoon.domain.member.domain.User
import com.fasterxml.jackson.annotation.JsonProperty
import java.time.LocalDateTime

data class MemberAndTokenResponse (
    var token: TokenResponse? = null,
    val isSign: Boolean,

    @JsonProperty("user_id")
    val userId: Long,
    val nickname: String,

    @JsonProperty("profile_img")
    val profileImg: String?,

    @JsonProperty("thumbnail_img")
    val thumbnailImg: String?,

    @JsonProperty("question_cnt")
    val questionCnt: Int,

    @JsonProperty("answer_cnt")
    val answerCnt: Int,

    @JsonProperty("alarm_on")
    val alarmOn: Boolean,

    @JsonProperty("created_at")
    val createdAt: LocalDateTime,

    @JsonProperty("updated_at")
    val updatedAt: LocalDateTime,
) {
    companion object {
        fun from(user: User, isSignup: Boolean = false): MemberAndTokenResponse {
            return MemberAndTokenResponse(
                isSign = isSignup,
                nickname = user.nickname!!,
                userId = user.id,
                profileImg = user.profileImg,
                thumbnailImg = user.thumbnailImg,
                questionCnt = user.questionCnt,
                answerCnt = user.answerCnt,
                createdAt = user.createdAt ?: LocalDateTime.now(),
                updatedAt = user.updatedAt ?: LocalDateTime.now(),
                alarmOn = user.fcmToken != null,
            )
        }
    }
}