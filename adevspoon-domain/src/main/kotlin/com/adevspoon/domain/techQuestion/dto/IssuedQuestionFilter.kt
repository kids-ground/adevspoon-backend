package com.adevspoon.domain.techQuestion.dto

data class IssuedQuestionFilter(
    val memberId: Long,
    val categoryIds: List<Long>,
    val isAnswered: Boolean?,
)
