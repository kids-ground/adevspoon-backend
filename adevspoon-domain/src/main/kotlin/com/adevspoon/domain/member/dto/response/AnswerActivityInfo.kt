package com.adevspoon.domain.member.dto.response

import java.time.LocalDate

data class AnswerActivityInfo(
    val date: LocalDate,
    val answerCount: Int
)