package com.adevspoon.domain.board.dto.request

data class UpdatePostRequestDto(
    val postId: Long,
    val title: String?,
    val content: String?,
    val tagId: Int?,
)
