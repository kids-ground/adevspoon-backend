package com.adevspoon.api.question.dto.request

data class QuestionRequest(
    val questionId: Long?,
    val type: String = "today",
)
