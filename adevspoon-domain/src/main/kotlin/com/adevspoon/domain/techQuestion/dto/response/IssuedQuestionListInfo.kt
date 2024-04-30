package com.adevspoon.domain.techQuestion.dto.response

data class IssuedQuestionListInfo(
    val list: List<QuestionInfo>,
    val hasNext: Boolean,
)
