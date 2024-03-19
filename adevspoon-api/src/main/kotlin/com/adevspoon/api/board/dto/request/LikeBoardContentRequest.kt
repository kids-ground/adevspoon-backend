package com.adevspoon.api.board.dto.request

data class LikeBoardContentRequest(
    val type: BoardContentType,
    val contentId: Long,
    val like: Boolean,
)