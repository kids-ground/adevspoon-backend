package com.adevspoon.api.member.dto.request

import com.adevspoon.domain.member.dto.request.MemberUpdateRequireDto
import org.springframework.web.multipart.MultipartFile

data class MemberProfileUpdateRequest(
    val image: MultipartFile?,
    val fcmToken: String?,
    val nickname: String?,
    val representativeBadge: Int?,
    val categoryIds: String?,
) {
    fun toMemberUpdateRequireDto(
        memberId: Long,
        profileUrl: String?,
        thumbnailUrl: String?
    ) = MemberUpdateRequireDto(
        memberId = memberId,
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
