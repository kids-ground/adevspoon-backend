package com.adevspoon.api.board.dto.request

import io.swagger.v3.oas.annotations.media.Schema
import jakarta.validation.constraints.Min
import jakarta.validation.constraints.NotBlank
import jakarta.validation.constraints.Size

data class UpdateBoardPostRequest(
    val postId: Long,
    @Schema(description = "수정한 제목")
    val title: String?,
    @Schema(description = "수정한 내용")
    @field:NotBlank(message = "댓글 내용을 입력해주세요.")
    @field:Size(max = 200, message = "댓글은 최대 200자까지 입력 가능합니다.")
    val content: String,
    @Schema(description = "수정한 태그 id")
    @field:Min(value = 1, message = "태그 id를 입력해주세요.")
    val tagId: Int,
)
