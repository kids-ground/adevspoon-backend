package com.adevspoon.api.board.dto.response

data class BoardListResponse(
    val list: List<BoardInfoResponse>,
    val next: String?,
)