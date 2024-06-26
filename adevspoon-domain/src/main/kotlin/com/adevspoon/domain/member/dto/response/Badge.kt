package com.adevspoon.domain.member.dto.response

import com.adevspoon.domain.member.domain.BadgeEntity


data class Badge(
    val id: Int,
    val name: String,
    var description: String? = null,
    var imageUrl: String? = null,
    var thumbnailUrl: String? = null,
    var silhouetteUrl: String? = null,
) {
    companion object {
        fun from(badge: BadgeEntity) = Badge(
            id = badge.id ?: -1,
            name = badge.name ?: "",
            description = badge.description,
            imageUrl = badge.imageUrl,
            thumbnailUrl = badge.thumbnailUrl,
            silhouetteUrl = badge.silhouetteUrl,
        )
    }
}