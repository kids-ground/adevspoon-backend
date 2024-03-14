package com.adevspoon.api.member.dto.response

import com.fasterxml.jackson.annotation.JsonProperty
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

    val badges: List<BadgeResponse>? = null,
    val profileBelt: String,
    val representativeBadge: String? = null,

    @JsonProperty("created_at")
    val createdAt: LocalDateTime,

    @JsonProperty("updated_at")
    val updatedAt: LocalDateTime,
)