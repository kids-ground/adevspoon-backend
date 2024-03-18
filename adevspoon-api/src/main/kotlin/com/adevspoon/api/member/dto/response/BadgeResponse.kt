package com.adevspoon.api.member.dto.response

import com.adevspoon.domain.member.dto.response.Badge

data class BadgeResponse(
    val id: Int,
    val name: String,
    val description: String? = null,
    val imageUrl: String,
    val thumbnailUrl: String,
    val silhouetteUrl: String,
) {
    companion object {
        fun from(badge: Badge) = BadgeResponse(
            id = badge.id,
            name = badge.name,
            description = badge.description,
            imageUrl = badge.imageUrl ?: "",
            thumbnailUrl = badge.thumbnailUrl ?: "",
            silhouetteUrl = badge.silhouetteUrl ?: ""
        )
    }
}
