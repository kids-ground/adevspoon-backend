package com.adevspoon.api.member.dto.response

data class AchievedBadgeResponse(
    val id: Int,
    val name: String,
    val description: String,
    val imageUrl: String,
    val thumbnailUrl: String,
    val silhouetteUrl: String,
    val criteriaValue: Int?,
    val isAcheived: Boolean,
    val userValue: Int,
    val isRepresentative: Boolean,
)
