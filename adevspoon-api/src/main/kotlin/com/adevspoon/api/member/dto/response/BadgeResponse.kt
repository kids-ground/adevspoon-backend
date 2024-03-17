package com.adevspoon.api.member.dto.response

import com.adevspoon.domain.member.dto.response.BadgeResponseDto

data class BadgeResponse(
    val id: Int,
    val name: String,
    val description: String? = null,
    val imageUrl: String,
    val thumbnailUrl: String,
    val silhouetteUrl: String,
) {
    companion object {
        fun from(badge: BadgeResponseDto) = BadgeResponse(
            id = badge.id,
            name = badge.name,
            description = badge.description,
            imageUrl = badge.imageUrl ?: "",
            thumbnailUrl = badge.thumbnailUrl ?: "",
            silhouetteUrl = badge.silhouetteUrl ?: ""
        )
    }
}
