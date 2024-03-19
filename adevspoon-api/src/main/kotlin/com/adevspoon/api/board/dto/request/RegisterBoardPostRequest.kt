package com.adevspoon.api.board.dto.request

data class RegisterBoardPostRequest(
    val title: String,
    val content: String,
    val tagId: Long,
)
