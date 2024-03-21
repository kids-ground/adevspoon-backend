package com.adevspoon.api.question.dto.request

data class QuestionCategoryListRequest(
    val offset: Int = 0,
    val limit: Int = 30,
)
