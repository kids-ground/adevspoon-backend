package com.adevspoon.api.member.dto.request

import com.adevspoon.domain.member.dto.request.MemberUpdateRequestDto
import org.springframework.web.multipart.MultipartFile

data class MemberProfileUpdateRequest(
    val image: MultipartFile?,
    val fcmToken: String?,
    val nickname: String?,
    val representativeBadge: Int?,
    val categoryIds: String?,
) {
    fun toMemberUpdateRequestDto(
        profileUrl: String?,
        thumbnailUrl: String?
    ) = MemberUpdateRequestDto(
        nickname = nickname,
        fcmToken = fcmToken,
        representativeBadge = representativeBadge,
        categoryIds = getCategoryIdList(),
        profileImageUrl = profileUrl,
        thumbnailImageUrl = thumbnailUrl
    )

    private fun getCategoryIdList(): List<Long> = categoryIds?.split(",")
        ?.map { it.trim() }
        ?.map { it.toLong() }
        ?: emptyList()
}
