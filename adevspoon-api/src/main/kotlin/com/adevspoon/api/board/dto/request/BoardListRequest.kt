package com.adevspoon.api.board.dto.request

import io.swagger.v3.oas.annotations.media.Schema

data class BoardListRequest(
    val tag: List<Long>,
    val take: Long,
    val startId: Long,
    @Schema(description = "유저 Id(게시글 작성자 Id)로 필터링")
    val userId: Long,
)
