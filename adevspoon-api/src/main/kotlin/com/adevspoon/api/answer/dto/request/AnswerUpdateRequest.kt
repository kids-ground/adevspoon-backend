package com.adevspoon.api.answer.dto.request

import io.swagger.v3.oas.annotations.media.Schema
import jakarta.validation.constraints.Size

data class AnswerUpdateRequest(
    @Schema(description = "무조건 answer", example = "answer", nullable = true, defaultValue = "answer")
    val postType: AnswerType = AnswerType.ANSWER,

    @Schema(description = "수정된 답변")
    @field:Size(min = 30, max = 500, message = "content는 30자 이상 500자 이하여야 합니다.")
    val content: String
)