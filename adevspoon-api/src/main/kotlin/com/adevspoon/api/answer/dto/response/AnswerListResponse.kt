package com.adevspoon.api.answer.dto.response

import io.swagger.v3.oas.annotations.media.Schema

data class AnswerListResponse(
    val list: List<AnswerInfoResponse>,
    @Schema(description = "다음 페이지가 있는 경우 해당 URL을 사용하여 다음 페이지를 요청할 수 있습니다.", example = "https://api.adevspoon.com/api/postList?questionId=10&sort=newest&offset=10&limit=20", nullable = true)
    val next: String?
)