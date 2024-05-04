package com.adevspoon.api.member.dto.response

import com.adevspoon.domain.member.dto.response.BadgeAchievedInfo
import com.fasterxml.jackson.annotation.JsonProperty
import io.swagger.v3.oas.annotations.media.Schema

data class AchievedBadgeResponse(
    val id: Int,
    val name: String,
    val description: String,
    val imageUrl: String,
    val thumbnailUrl: String,
    @Schema(description = "뱃지 실루엣 이미지")
    val silhouetteUrl: String,
    @JsonProperty("isAcheived")
    @Schema(description = "뱃지 획득 여부")
    val isAchieved: Boolean,
    @Schema(description = "뱃지 획득을 위한 달성 기준 일수")
    val criteriaValue: Int?,
    @Schema(description = "뱃지 획득을 위한 현재 유저가 달성한 수")
    val userValue: Int,
    @Schema(description = "현재 대표뱃지로 설정했는지 여부")
    val isRepresentative: Boolean,
) {
    companion object {
        fun from(badge: BadgeAchievedInfo) = AchievedBadgeResponse(
            id = badge.id,
            name = badge.name,
            description = badge.description,
            imageUrl = badge.imageUrl,
            thumbnailUrl = badge.thumbnailUrl,
            silhouetteUrl = badge.silhouetteUrl,
            isAchieved = badge.isAchieved,
            criteriaValue = badge.criteriaValue,
            userValue = badge.userValue,
            isRepresentative = badge.isRepresentative
        )
    }
}
