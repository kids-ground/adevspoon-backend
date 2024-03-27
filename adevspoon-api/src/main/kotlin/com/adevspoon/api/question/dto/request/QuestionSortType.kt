package com.adevspoon.api.question.dto.request

import com.fasterxml.jackson.annotation.JsonValue

enum class QuestionSortType(
    @JsonValue val value: String
) {
    NEWEST("newest"),
    OLDEST("oldest");
}