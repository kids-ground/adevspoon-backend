package com.adevspoon.domain.techQuestion.dto.request

import com.adevspoon.domain.common.lock.IssueQuestionLockKey
import java.time.LocalDate

data class GetTodayQuestion(
    val memberId: Long,
    val today: LocalDate
): IssueQuestionLockKey {
    override val keyMemberId: Long = memberId
}
