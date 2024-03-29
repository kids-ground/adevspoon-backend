package com.adevspoon.api.answer.dto.request

import com.adevspoon.api.common.dto.LegacyDtoEnum


enum class AnswerSortType: LegacyDtoEnum {
    NEWEST,
    OLDEST,
    BEST,
}