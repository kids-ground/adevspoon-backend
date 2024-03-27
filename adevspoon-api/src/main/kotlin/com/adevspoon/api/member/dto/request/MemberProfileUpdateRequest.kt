package com.adevspoon.api.member.dto.request

import com.adevspoon.domain.member.dto.request.MemberUpdateRequireDto
import io.swagger.v3.oas.annotations.media.Schema
import org.springframework.web.multipart.MultipartFile

data class MemberProfileUpdateRequest(
    @Schema(description = "수정할 이미지 파일")
    val image: MultipartFile?,
    val fcmToken: String?,
    val nickname: String?,
    @Schema(description = "수정할 대표 뱃지 id", example = "1")
    val representativeBadge: Int?,
    @Schema(description = "수정할 카테고리 id들(콤마로 구분)", example = "1,2,3")
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
