package com.adevspoon.api.question.dto.response

import io.swagger.v3.oas.annotations.media.Schema

data class QuestionCategoryResponse(
    val id: Long,
    val name: String,
    @Schema(description = "현재 유저기준 해당 카테고리에 더 이상 받을 질문이 없는지")
    val depleted: Boolean,
    @Schema(description = "현재 질문 받고 있는 카테고리인지")
    val selected: Boolean
)
