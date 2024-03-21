package com.adevspoon.api.answer.dto.request

import com.fasterxml.jackson.annotation.JsonValue
import io.swagger.v3.oas.annotations.media.Schema

// TODO: Enum Validation 필요
data class LikeRequest(
    @Schema(description = "좋아요 타입", example = "answer", nullable = false)
    val type: LikeType,
    @Schema(description = "좋아요인지 취소인지", example = "true", nullable = true, defaultValue = "true")
    val like: Boolean = true
)

enum class LikeType(
    @JsonValue val value: String
) {
    ANSWER("answer"),
    BOARD_POST("board_post"),
}