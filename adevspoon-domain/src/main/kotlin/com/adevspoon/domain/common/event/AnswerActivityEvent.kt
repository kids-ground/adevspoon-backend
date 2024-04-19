package com.adevspoon.domain.common.event

data class AnswerActivityEvent(
    val memberId: Long,
    val answerId: Long,
)