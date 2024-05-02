package com.adevspoon.api.answer.dto.request

import com.adevspoon.api.common.dto.LegacyDtoEnum
import com.adevspoon.domain.techQuestion.dto.enums.QuestionAnswerListSortType


enum class AnswerSortType: LegacyDtoEnum {
    NEWEST,
    OLDEST,
    BEST,
    LIKE;

    fun toQuestionAnswerListSortType(): QuestionAnswerListSortType {
        return when (this) {
            NEWEST -> QuestionAnswerListSortType.NEWEST
            OLDEST -> QuestionAnswerListSortType.OLDEST
            BEST -> QuestionAnswerListSortType.BEST
            LIKE -> QuestionAnswerListSortType.LIKE
        }
    }
}