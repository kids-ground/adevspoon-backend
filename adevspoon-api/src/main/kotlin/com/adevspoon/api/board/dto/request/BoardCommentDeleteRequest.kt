package com.adevspoon.api.board.dto.request

import jakarta.validation.constraints.Min
import jakarta.validation.constraints.NotNull

data class BoardCommentDeleteRequest(
    @field:NotNull(message = "댓글 id를 입력해주세요.")
    @field:Min(value = 1, message = "댓글 id를 입력해주세요.")
    val commentId: Long,
)
