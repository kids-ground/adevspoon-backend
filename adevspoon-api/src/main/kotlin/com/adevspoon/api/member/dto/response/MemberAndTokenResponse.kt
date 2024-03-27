package com.adevspoon.api.member.dto.response

import com.adevspoon.api.auth.dto.response.TokenResponse
import com.adevspoon.domain.member.dto.response.MemberAndSignup
import com.fasterxml.jackson.annotation.JsonProperty
import io.swagger.v3.oas.annotations.media.Schema
import java.time.LocalDateTime

data class MemberAndTokenResponse (
    @Schema(description = "토큰 정보(accessToken, refreshToken)")
    val token: TokenResponse,
    @Schema(description = "회원가입인지 로그인인지 여부")
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

    @Schema(description = "푸시알림 설정 여부")
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