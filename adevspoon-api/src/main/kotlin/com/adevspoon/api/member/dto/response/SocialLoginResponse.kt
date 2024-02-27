package com.adevspoon.api.member.dto.response

import com.adevspoon.domain.user.domain.User
import com.fasterxml.jackson.annotation.JsonProperty
import java.time.LocalDateTime

data class SocialLoginResponse (
    var token: ServiceToken? = null,

    val isSign: Boolean,
    val nickname: String,

    @JsonProperty("user_id")
    val userId: Long,

    @JsonProperty("profile_img")
    val profileImg: String?,

    @JsonProperty("thumbnail_img")
    val thumbnailImg: String?,

    @JsonProperty("question_cnt")
    val questionCnt: Int,

    @JsonProperty("answer_cnt")
    val answerCnt: Int,

    @JsonProperty("created_at")
    val createdAt: LocalDateTime,

    @JsonProperty("updated_at")
    val updatedAt: LocalDateTime,

    @JsonProperty("alarm_on")
    val alarmOn: Boolean,
) {
    companion object {
        fun from(user: User, isSignup: Boolean = false): SocialLoginResponse {
            return SocialLoginResponse(
                isSign = isSignup,
                nickname = user.nickname!!,
                userId = user.id,
                profileImg = user.profileImg,
                thumbnailImg = user.thumbnailImg,
                questionCnt = user.questionCnt ?: 0,
                answerCnt = user.answerCnt ?: 0,
                createdAt = user.createdAt ?: LocalDateTime.now(),
                updatedAt = user.updatedAt ?: LocalDateTime.now(),
                alarmOn = user.fcmToken != null,
            )
        }
    }

}

data class ServiceToken (
    val accessToken: String,
    val refreshToken: String,
)