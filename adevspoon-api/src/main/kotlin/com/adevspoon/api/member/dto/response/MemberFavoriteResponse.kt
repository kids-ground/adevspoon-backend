package com.adevspoon.api.member.dto.response

import io.swagger.v3.oas.annotations.media.Schema
import java.time.LocalDate

// TODO: Enum Validation 필요
data class MemberFavoriteResponse(
    val id: Int,
    @Schema(description = "포스트 타입")
    val postType: PostType,
    val postId: Int,
    val title: String,
    @Schema(description = "포스트의 컨텐트")
    val content: String,
    @Schema(description = "좋아요한 날짜")
    val date: LocalDate,
    @Schema(description = "작성자 정보")
    val writer: MemberProfileResponse,
    val isLiked: Boolean?,
    val likeCount: Int,
)
