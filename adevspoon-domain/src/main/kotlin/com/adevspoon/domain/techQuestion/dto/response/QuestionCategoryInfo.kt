package com.adevspoon.domain.techQuestion.dto.response

import com.adevspoon.domain.techQuestion.domain.QuestionCategoryEntity

data class QuestionCategoryInfo(
    val id: Long,
    val name: String,
    val depleted: Boolean = false,
    val selected: Boolean = false
) {
    companion object {
        fun from(category: QuestionCategoryEntity, depleted: Boolean, selected: Boolean): QuestionCategoryInfo {
            return QuestionCategoryInfo(category.id, category.category, depleted, selected)
        }
    }
}