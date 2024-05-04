package com.adevspoon.domain.member.dto.response

import com.adevspoon.domain.member.domain.BadgeEntity


data class BadgeAchievedInfo(
    val id: Int,
    val name: String,
    val description: String,
    val imageUrl: String,
    val thumbnailUrl: String,
    val silhouetteUrl: String,
    val criteriaValue: Int?,
    val userValue: Int,
    val isAchieved: Boolean,
    val isRepresentative: Boolean = false,
) {
    companion object {
        fun from(
            badge: BadgeEntity,
            isAchieved: Boolean,
            isRepresentative: Boolean,
            userValue: Int,
        ) = BadgeAchievedInfo(
            id = badge.id,
            name = badge.name ?: "",
            description = badge.description ?: "",
            imageUrl = badge.imageUrl ?: "",
            thumbnailUrl = badge.thumbnailUrl ?: "",
            silhouetteUrl = badge.silhouetteUrl ?: "",
            criteriaValue = badge.criteriaValue,
            userValue = userValue,
            isAchieved = isAchieved,
            isRepresentative = isRepresentative,
        )
    }
}
