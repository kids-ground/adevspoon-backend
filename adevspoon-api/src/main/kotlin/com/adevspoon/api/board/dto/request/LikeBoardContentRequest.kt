package com.adevspoon.api.board.dto.request

import com.adevspoon.domain.board.dto.request.UpdateLikeStateRequest
import io.swagger.v3.oas.annotations.media.Schema
import jakarta.validation.constraints.NotNull
import jakarta.validation.constraints.Positive

// TODO : enum validation 필요
data class LikeBoardContentRequest(
    @Schema(description = "게시글 or 댓글", example = "board_post", required = true)
    val type: BoardContentType,
    @Schema(description = "타입에 따른 postId", required = true)
    @field:NotNull(message = "게시글 or 댓글 id는 필수입니다.")
    @field:Positive(message = "게시글 or 댓글 id는 양수입니다.")
    val contentId: Long,
    @Schema(description = "좋아요 상태를 지정합니다. 'true'는 좋아요를 추가하라는 요청이며, 'false'는 좋아요를 취소하라는 요청입니다.",
        example = "true", nullable = true, defaultValue = "true")
    val like: Boolean = true,
) {
    fun toLikeStateRequest() = UpdateLikeStateRequest(
        type = when (type) {
            BoardContentType.BOARD_POST -> BoardContentType.BOARD_POST.toString()
            BoardContentType.BOARD_COMMENT -> BoardContentType.BOARD_COMMENT.toString()
        },
        contentId = this.contentId,
        like = this.like
    )
}