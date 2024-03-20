package com.adevspoon.api.member.dto.response

import java.time.LocalDate

data class MemberFavoriteResponse(
    val id: Int,
    val postType: PostType,
    val postId: Int,
    val title: String,
    val content: String,
    val date: LocalDate,
    val writer: MemberProfileResponse,
    val isLiked: Boolean?,
    val likeCount: Int,
)
