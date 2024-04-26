package com.adevspoon.domain.board.dto.request

class UpdateCommentLikeStateRequest(
    val type: String,
    val contentId: Long,
    val like: Boolean
)
