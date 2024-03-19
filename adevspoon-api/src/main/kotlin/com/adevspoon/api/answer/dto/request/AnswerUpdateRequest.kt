package com.adevspoon.api.answer.dto.request

data class AnswerUpdateRequest(
    val postType: PostType = PostType.ANSWER,
    val content: String? = null,
)