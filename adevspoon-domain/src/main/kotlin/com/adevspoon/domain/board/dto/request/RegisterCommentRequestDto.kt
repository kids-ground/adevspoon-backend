package com.adevspoon.domain.board.dto.request

data class RegisterCommentRequestDto(
    val postId: Long,
    val content: String
)
