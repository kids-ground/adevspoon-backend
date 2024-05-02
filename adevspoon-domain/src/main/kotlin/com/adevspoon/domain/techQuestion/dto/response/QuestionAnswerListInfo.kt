package com.adevspoon.domain.techQuestion.dto.response

data class QuestionAnswerListInfo(
    val list: List<QuestionAnswerInfo>,
    val hasNext: Boolean,
)
