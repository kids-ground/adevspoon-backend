package com.adevspoon.domain.techQuestion.dto.response

data class CategoryQuestionCountDto(
    val categoryId: Long,
    val questionCount: Long
) {
    operator fun minus(right: CategoryQuestionCountDto): CategoryQuestionCountDto {
        if (categoryId != right.categoryId) return this
        return CategoryQuestionCountDto(categoryId, questionCount - right.questionCount)
    }

    val isDepleted: Boolean = questionCount <= 0
}

fun List<CategoryQuestionCountDto>.subtractCount(right: List<CategoryQuestionCountDto>): List<CategoryQuestionCountDto> {
    return this.map { left ->
        right.find { it.categoryId == left.categoryId }?.let { right ->
            left - right
        } ?: left
    }
}