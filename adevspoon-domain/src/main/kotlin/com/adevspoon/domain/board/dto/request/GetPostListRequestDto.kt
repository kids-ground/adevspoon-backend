package com.adevspoon.domain.board.dto.request

data class GetPostListRequestDto(
    val tags: List<Int>,
    val pageSize: Int = 10,
    val startPostId: Long? = null,
    val targetUserId: Long?
)
