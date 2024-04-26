package com.adevspoon.domain.board.dto.request

data class UpdateLikeStateRequest(
    val type: String,
    val contentId: Long,
    val like: Boolean
)
