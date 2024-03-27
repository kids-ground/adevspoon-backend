package com.adevspoon.api.board.dto.request

import com.adevspoon.api.answer.dto.request.PostReportType
import io.swagger.v3.oas.annotations.media.Schema
import jakarta.validation.constraints.NotBlank

// TODO: Enum validation 필요
data class ReportBoardContentRequest(
    @Schema(description = "게시글 or 댓글", example = "board_post", required = true,)
    val type: BoardContentType,
    @Schema(description = "타입에 따른 postId", required = true)
    @field:NotBlank(message = "게시글 or 댓글 id는 필수입니다.")
    val contentId: Long,
    @Schema(description = "신고 이유", example = "none", nullable = true, defaultValue = "none")
    val reason: PostReportType = PostReportType.NONE,
)