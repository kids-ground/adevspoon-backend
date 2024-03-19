package com.adevspoon.api.board.dto.request

data class UpdateBoardPostRequest(
    val postId: Long,
    val title: String?,
    val content: String?,
    val tagId: Int?,
)
