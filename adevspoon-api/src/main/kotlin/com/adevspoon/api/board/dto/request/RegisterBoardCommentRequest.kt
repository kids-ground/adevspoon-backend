package com.adevspoon.api.board.dto.request

import com.adevspoon.domain.board.dto.request.RegisterCommentRequestDto
import io.swagger.v3.oas.annotations.media.Schema
import jakarta.validation.constraints.Min
import jakarta.validation.constraints.NotBlank
import jakarta.validation.constraints.NotNull
import jakarta.validation.constraints.Size

data class RegisterBoardCommentRequest(
    @field:NotNull(message = "게시글 id를 입력해주세요.")
    @field:Min(value = 1, message = "게시글 id를 입력해주세요.")
    val postId: Long,
    @Schema(description = "댓글 내용", required = true)
    @field:NotBlank(message = "댓글 내용을 입력해주세요.")
    @field:Size(max = 200, message = "댓글은 최대 200자까지 입력 가능합니다.")
    val content: String,
) {
    fun toRegisterCommentRequestDto() = RegisterCommentRequestDto(
        postId = this.postId,
        content = this.content
    )
}
