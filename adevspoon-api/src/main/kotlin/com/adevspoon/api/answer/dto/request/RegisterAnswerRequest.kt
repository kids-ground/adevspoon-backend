package com.adevspoon.api.answer.dto.request

data class RegisterAnswerRequest(
    val questionId: String,
    val content: String,
    val type: PostType = PostType.ANSWER,
)