package com.adevspoon.api.answer.dto.request

import com.fasterxml.jackson.annotation.JsonValue

enum class AnswerSortType(
    @JsonValue val value: String
) {
    NEWEST("newest"),
    OLDEST("oldest"),
    BEST("best"),
}