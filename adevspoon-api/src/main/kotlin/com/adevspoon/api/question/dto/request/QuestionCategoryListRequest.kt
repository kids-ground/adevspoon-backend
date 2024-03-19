package com.adevspoon.api.question.dto.request

data class QuestionCategoryListRequest(
    // TODO: Page 가능한 클래스 상속
    val offset: Int = 0,
    val limit: Int = 30,
)
