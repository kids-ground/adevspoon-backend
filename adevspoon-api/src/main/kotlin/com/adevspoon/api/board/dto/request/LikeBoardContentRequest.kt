package com.adevspoon.api.board.dto.request

import com.adevspoon.domain.board.dto.request.UpdateCommentLikeStateRequest
import com.adevspoon.domain.board.dto.request.UpdatePostLikeStateRequest
import io.swagger.v3.oas.annotations.media.Schema
import jakarta.validation.constraints.NotNull
import jakarta.validation.constraints.Positive

// TODO : enum validation 필요
data class LikeBoardContentRequest(
    @Schema(description = "게시글 or 댓글", example = "board_post", required = true,)
    val type: BoardContentType,
    @Schema(description = "타입에 따른 postId", required = true)
    @field:NotNull(message = "게시글 or 댓글 id는 필수입니다.")
    @field:Positive(message = "게시글 or 댓글 id는 양수입니다.")
    val contentId: Long,
    @Schema(description = "좋아요 여부", example = "true", nullable = true, defaultValue = "true")
    val like: Boolean = true,
) {
    fun toUpdatePostLikeStateRequest() = UpdatePostLikeStateRequest(
        type = BoardContentType.BOARD_POST.toString(),
        contentId = this.contentId,
        like = this.like
    )

    fun toUpdateCommentLikeStateRequest() = UpdateCommentLikeStateRequest(
        type = BoardContentType.BOARD_COMMENT.toString(),
        contentId = this.contentId,
        like = this.like
    )
}