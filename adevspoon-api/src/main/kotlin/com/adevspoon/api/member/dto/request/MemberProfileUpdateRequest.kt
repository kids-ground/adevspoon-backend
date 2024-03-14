package com.adevspoon.api.member.dto.request

import org.springframework.web.multipart.MultipartFile

data class MemberProfileUpdateRequest(
    val image: MultipartFile?,
    val fcmToken: String?,
    val nickname: String?,
    val representativeBadge: Int?,
    val categoryIds: String?,
) {
    fun getCategoryIdList(): List<Long> = categoryIds?.split(",")
        ?.map { it.trim() }
        ?.map { it.toLong() }
        ?: emptyList()
}
