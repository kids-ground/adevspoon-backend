package com.adevspoon.api.board.dto.request

import io.swagger.v3.oas.annotations.media.Schema

data class UpdateBoardPostRequest(
    val postId: Long,
    @Schema(description = "수정한 제목")
    val title: String?,
    @Schema(description = "수정한 내용")
    val content: String?,
    @Schema(description = "수정한 태그 id")
    val tagId: Int?,
)
