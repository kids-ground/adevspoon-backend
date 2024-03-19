package com.adevspoon.api.member.dto.response

import com.adevspoon.api.auth.dto.response.TokenResponse
import com.adevspoon.domain.member.dto.response.MemberAndSignup
import com.fasterxml.jackson.annotation.JsonProperty
import java.time.LocalDateTime

data class MemberAndTokenResponse (
    val token: TokenResponse,
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
        fun from(member: MemberAndSignup, token: TokenResponse): MemberAndTokenResponse {
            return MemberAndTokenResponse(
                isSign = member.isSign,
                nickname = member.nickname,
                userId = member.memberId,
                profileImg = member.profileImg,
                thumbnailImg = member.thumbnailImg,
                questionCnt = member.questionCnt,
                answerCnt = member.answerCnt,
                createdAt = member.createdAt,
                updatedAt = member.updatedAt,
                alarmOn = member.alarmOn,
                token = token
            )
        }
    }
}