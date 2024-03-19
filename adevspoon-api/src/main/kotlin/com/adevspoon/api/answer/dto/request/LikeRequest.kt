package com.adevspoon.api.answer.dto.request

import com.fasterxml.jackson.annotation.JsonValue

data class LikeRequest(
    val type: Long,
    val like: Boolean = true
)

enum class LikeType(
    @JsonValue val value: String
) {
    ANSWER("answer"),
    BOARD_POST("board_post"),
}