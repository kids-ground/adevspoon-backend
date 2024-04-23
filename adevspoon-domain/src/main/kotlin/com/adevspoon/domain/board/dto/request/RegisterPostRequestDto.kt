package com.adevspoon.domain.board.dto.request

data class RegisterPostRequestDto (
    val title: String,
    val content: String,
    val tagId: Int
)