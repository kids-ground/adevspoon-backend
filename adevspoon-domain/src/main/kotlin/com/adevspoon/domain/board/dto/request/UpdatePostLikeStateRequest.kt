package com.adevspoon.domain.board.dto.request

data class UpdatePostLikeStateRequest(
    val type: String,
    val contentId: Long,
    val like: Boolean
)
