package com.adevspoon.domain.member.dto.response

import java.time.LocalDate

data class LikeInfo(
    val id: Long,
    val postType: String,
    val postId: Long,
    val title: String,
    val content: String,
    val date: LocalDate,
    val writer: MemberProfile,
    val isLiked: Boolean?,
    val likeCount: Int,
)
