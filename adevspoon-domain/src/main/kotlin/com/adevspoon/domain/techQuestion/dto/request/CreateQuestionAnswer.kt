package com.adevspoon.domain.techQuestion.dto.request

data class CreateQuestionAnswer(
    val requestMemberId: Long,
    val questionId: Long,
    val answer: String,
)