package com.adevspoon.api.answer.dto.request

import io.swagger.v3.oas.annotations.media.Schema

data class LikeRequest(
    @Schema(description = "좋아요 타입", example = "answer", nullable = false)
    val type: LikeType = LikeType.ANSWER,
    @Schema(description = "좋아요인지 취소인지", example = "true", nullable = true, defaultValue = "true")
    val like: Boolean = true
)
