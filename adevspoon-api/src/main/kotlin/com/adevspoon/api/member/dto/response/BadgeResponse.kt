package com.adevspoon.api.member.dto.response

data class BadgeResponse(
    val id: Int,
    val name: String,
    val description: String? = null,
    val imageUrl: String,
    val thumbnailUrl: String,
    val silhouetteUrl: String,
)
