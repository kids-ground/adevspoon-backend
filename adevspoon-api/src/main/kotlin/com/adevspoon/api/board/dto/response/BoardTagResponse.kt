package com.adevspoon.api.board.dto.response

import io.swagger.v3.oas.annotations.media.Schema

data class BoardTagResponse(
    val id: Int,
    val name: String,
    @Schema(description = "태그를 의미하는 이모지")
    val emoji: String,
)
