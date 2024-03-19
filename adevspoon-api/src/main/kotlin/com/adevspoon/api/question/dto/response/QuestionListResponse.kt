package com.adevspoon.api.question.dto.response

data class QuestionListResponse(
    val list: List<QuestionInfoResponse>,
    val next: String,
)
