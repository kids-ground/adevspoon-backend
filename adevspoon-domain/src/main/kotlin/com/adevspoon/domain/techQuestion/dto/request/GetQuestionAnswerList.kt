package com.adevspoon.domain.techQuestion.dto.request

import com.adevspoon.domain.techQuestion.dto.enums.QuestionAnswerListSortType

data class GetQuestionAnswerList(
    val memberId: Long,
    val questionId: Long?,
    val sort: QuestionAnswerListSortType,
    val offset: Int = 0,
    val limit: Int = 10,
)
