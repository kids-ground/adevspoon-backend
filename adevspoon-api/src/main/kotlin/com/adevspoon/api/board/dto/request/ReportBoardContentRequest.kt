package com.adevspoon.api.board.dto.request

import com.adevspoon.api.answer.dto.request.PostReportType
import com.adevspoon.domain.board.dto.request.CreateReportRequest
import io.swagger.v3.oas.annotations.media.Schema
import jakarta.validation.constraints.Min
import jakarta.validation.constraints.NotNull

// TODO: Enum validation 필요
data class ReportBoardContentRequest(
    @Schema(description = "게시글 or 댓글", example = "board_post", required = true,)
    val type: BoardContentType,
    @Schema(description = "타입에 따른 postId", required = true)
    @field:NotNull(message = "게시글 or 댓글 id는 필수입니다.")
    @field:Min(1, message = "게시글 or 댓글 id는 0보다 커야합니다.")
    val contentId: Long,
    @Schema(description = "신고 이유", example = "none", nullable = true, defaultValue = "none")
    val reason: PostReportType = PostReportType.NONE,
) {
    fun toCreateReportRequest() = CreateReportRequest(
        type = when (type) {
            BoardContentType.BOARD_POST -> BoardContentType.BOARD_POST.toString()
            BoardContentType.BOARD_COMMENT -> BoardContentType.BOARD_COMMENT.toString()
        },
        contentId = this.contentId,
    )
}