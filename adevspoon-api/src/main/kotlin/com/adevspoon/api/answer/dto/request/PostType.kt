package com.adevspoon.api.answer.dto.request

import com.fasterxml.jackson.annotation.JsonValue

enum class PostType(
    @JsonValue val value: String
) {
    ANSWER("answer"),
    BOARD_POST("board_post")
}
