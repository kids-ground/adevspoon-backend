package com.adevspoon.api.member.dto.response

import com.adevspoon.domain.member.dto.response.MemberProfile
import com.fasterxml.jackson.annotation.JsonProperty
import io.swagger.v3.oas.annotations.media.Schema
import java.time.LocalDateTime

data class MemberProfileResponse(
    @JsonProperty("user_id")
    val userId: Long,
    val nickname: String,

    val statusMessage: String? = null,

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

    @Schema(description = "획득한 뱃지 목록")
    val badges: List<BadgeResponse>? = null,
    @Schema(description = "프로필 벨트", example = "none")
    val profileBelt: String,
    @Schema(description = "현재 대표 뱃지 정보")
    val representativeBadge: BadgeResponse? = null,

    @JsonProperty("created_at")
    val createdAt: LocalDateTime,

    @JsonProperty("updated_at")
    val updatedAt: LocalDateTime,
) {
    companion object {
        fun from(profile: MemberProfile) = MemberProfileResponse(
            userId = profile.memberId,
            nickname = profile.nickname,
            statusMessage = profile.statusMessage,
            profileImg = profile.profileImageUrl,
            thumbnailImg = profile.thumbnailImageUrl,
            questionCnt = profile.questionCnt,
            answerCnt = profile.answerCnt,
            alarmOn = profile.alarmOn,
            profileBelt = profile.profileBelt,
            badges = profile.badges?.map { BadgeResponse.from(it) },
            representativeBadge = profile.representativeBadge?.let { BadgeResponse.from(it) },
            createdAt = profile.createdAt,
            updatedAt = profile.updatedAt
        )
    }
}