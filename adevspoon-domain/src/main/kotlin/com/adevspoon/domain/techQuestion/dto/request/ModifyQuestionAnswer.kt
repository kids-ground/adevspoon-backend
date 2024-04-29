package com.adevspoon.domain.techQuestion.dto.request

data class ModifyQuestionAnswer(
    val memberId: Long,
    val answerId: Long,
    val answer: String,
)
