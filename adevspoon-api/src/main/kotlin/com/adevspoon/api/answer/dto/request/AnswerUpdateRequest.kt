package com.adevspoon.api.answer.dto.request

data class AnswerUpdateRequest(
    val postType: AnswerType = AnswerType.ANSWER,
    val content: String? = null,
)