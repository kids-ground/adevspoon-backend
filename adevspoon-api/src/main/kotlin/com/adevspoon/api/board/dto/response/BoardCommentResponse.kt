package com.adevspoon.api.board.dto.response

import com.adevspoon.api.member.dto.response.MemberProfileResponse
import java.time.LocalDateTime

data class BoardCommentResponse(
    val id: Long,
    val postId: Long,
    val content: String,
    val user: MemberProfileResponse,
    val isLiked: Boolean,
    val isMine: Boolean? = false,
    val likeCount: Int,
    val createdAt: LocalDateTime,
    val updatedAt: LocalDateTime,
)
