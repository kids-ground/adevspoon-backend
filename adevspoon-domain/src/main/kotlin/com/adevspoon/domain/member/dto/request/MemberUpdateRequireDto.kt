package com.adevspoon.domain.member.dto.request

data class MemberUpdateRequireDto(
    val memberId: Long,
    val nickname: String?,
    val fcmToken: String?,
    val representativeBadge: Int?,
    val categoryIds: List<Long>?,
    val profileImageUrl: String?,
    val thumbnailImageUrl: String?,
)
