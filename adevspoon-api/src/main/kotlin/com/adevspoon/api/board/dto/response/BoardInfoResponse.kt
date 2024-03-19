package com.adevspoon.api.board.dto.response

import com.adevspoon.api.member.dto.response.MemberProfileResponse
import java.time.LocalDateTime

data class BoardInfoResponse(
    val id: Long,
    val tag: BoardTagResponse,
    val title: String,
    val content: String,
    val likeCount: Int,
    val commentCount: Int,
    val isLiked: Boolean,
    val createdAt: LocalDateTime,
    val updatedAt: LocalDateTime,
    val user: MemberProfileResponse
)
