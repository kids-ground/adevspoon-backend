package com.adevspoon.api.member.dto.response

import com.adevspoon.domain.member.dto.response.AnswerActivityInfo
import java.time.LocalDate

data class MemberActivityResponse(
    val date: LocalDate,
    val answerCount: Int,
) {
    companion object {
        fun from(answerActivityInfo: AnswerActivityInfo) = MemberActivityResponse(
            date = answerActivityInfo.date,
            answerCount = answerActivityInfo.answerCount
        )
    }
}
