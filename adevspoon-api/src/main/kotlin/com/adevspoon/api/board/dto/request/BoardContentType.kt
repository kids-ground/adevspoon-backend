package com.adevspoon.api.board.dto.request

import com.fasterxml.jackson.annotation.JsonValue

enum class BoardContentType(
    @JsonValue val value: String
) {
    BOARD_COMMENT("board_comment"),
    BOARD_POST("board_post"),
}