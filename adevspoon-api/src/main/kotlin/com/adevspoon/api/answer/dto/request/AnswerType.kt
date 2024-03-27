package com.adevspoon.api.answer.dto.request

import com.fasterxml.jackson.annotation.JsonValue

enum class AnswerType(
    @JsonValue val value: String
) {
    ANSWER("answer")
}
