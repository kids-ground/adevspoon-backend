package com.adevspoon.api.member.dto.response

import java.time.LocalDate

data class MemberActivityResponse(
    val date: LocalDate,
    val answerCount: Int,
)
