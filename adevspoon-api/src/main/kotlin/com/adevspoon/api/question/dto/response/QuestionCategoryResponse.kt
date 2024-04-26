package com.adevspoon.api.question.dto.response

import com.adevspoon.domain.techQuestion.dto.response.QuestionCategoryInfo
import io.swagger.v3.oas.annotations.media.Schema

data class QuestionCategoryResponse(
    val id: Long,
    val name: String,
    @Schema(description = "현재 유저가 해당 카테고리에 더 이상 받을 질문이 없는지 여부")
    val depleted: Boolean,
    @Schema(description = "현재 선택한 카테고리인지 여부")
    val selected: Boolean
) {
    companion object {
        fun from(info: QuestionCategoryInfo): QuestionCategoryResponse {
            return QuestionCategoryResponse(
                id = info.id,
                name = info.name,
                depleted = info.depleted,
                selected = info.selected
            )
        }
    }
}
