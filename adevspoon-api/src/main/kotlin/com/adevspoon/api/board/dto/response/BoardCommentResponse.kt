package com.adevspoon.api.board.dto.response

import com.adevspoon.api.member.dto.response.MemberProfileResponse
import io.swagger.v3.oas.annotations.media.Schema
import java.time.LocalDateTime

data class BoardCommentResponse(
    val id: Long,
    @Schema(description = "게시글 ID")
    val postId: Long,
    val content: String,
    @Schema(description = "답변 작성자 프로필정보")
    val user: MemberProfileResponse,
    @Schema(description = "좋아요를 눌렀는지 여부")
    val isLiked: Boolean,
    @Schema(description = "내 답변인지 여부", nullable = true)
    val isMine: Boolean?,
    @Schema(description = "총 좋아요 수")
    val likeCount: Int,
    val createdAt: LocalDateTime,
    val updatedAt: LocalDateTime,
)