package com.adevspoon.api.question.dto.request

import com.adevspoon.api.common.dto.LegacyDtoEnum
import com.adevspoon.domain.techQuestion.dto.enums.IssuedQuestionSortType


enum class QuestionSortType: LegacyDtoEnum {
    NEWEST,
    OLDEST;

    fun toIssuedQuestionSortType(): IssuedQuestionSortType {
        return when (this) {
            NEWEST -> IssuedQuestionSortType.NEWEST
            OLDEST -> IssuedQuestionSortType.OLDEST
        }
    }
}