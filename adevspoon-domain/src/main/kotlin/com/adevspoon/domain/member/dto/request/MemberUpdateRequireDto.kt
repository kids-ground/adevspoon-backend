package com.adevspoon.domain.member.dto.request

data class MemberUpdateRequireDto(
    val memberId: Long,
    val nickname: String? = null,
    val fcmToken: String? = null,
    val representativeBadge: Int? = null,
    val categoryIds: List<Long>? = null,
    val profileImageUrl: String? = null,
    val thumbnailImageUrl: String? = null,
)
