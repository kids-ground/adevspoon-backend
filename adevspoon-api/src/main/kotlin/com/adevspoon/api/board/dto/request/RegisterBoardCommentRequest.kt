package com.adevspoon.api.board.dto.request

data class RegisterBoardCommentRequest(
    val postId: Long,
    val content: String,
)
