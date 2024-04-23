package com.adevspoon.api.board.dto.request

import com.adevspoon.domain.board.dto.request.RegisterPostRequestDto
import io.swagger.v3.oas.annotations.media.Schema
import jakarta.validation.constraints.Min
import jakarta.validation.constraints.NotBlank
import jakarta.validation.constraints.Size

data class RegisterBoardPostRequest(
    @Schema(description = "제목", required = true)
    @field:NotBlank(message = "제목을 입력해주세요.")
    @field:Size(max = 30, message = "제목은 최대 30자까지 입력 가능합니다.")
    val title: String,
    @Schema(description = "내용", required = true)
    @field:NotBlank(message = "댓글 내용을 입력해주세요.")
    @field:Size(max = 200, message = "댓글은 최대 200자까지 입력 가능합니다.")
    val content: String,
    @Schema(description = "태그 id", required = true)
    @field:Min(value = 1, message = "태그 id를 입력해주세요.")
    val tagId: Int,
) {
    fun toRegisterPostRequestDto() = RegisterPostRequestDto(
        title = title,
        content = content,
        tagId = tagId
    )
}
