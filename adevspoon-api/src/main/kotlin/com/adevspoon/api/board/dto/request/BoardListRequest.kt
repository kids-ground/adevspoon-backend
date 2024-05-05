package com.adevspoon.api.board.dto.request

import com.adevspoon.domain.board.dto.request.GetPostListRequestDto
import io.swagger.v3.oas.annotations.media.Schema
import jakarta.validation.constraints.Positive

data class BoardListRequest(
    @Schema(description = "게시글 태그로 필터링 - 선택한 태그들의 id를 배열로 전달, 비어있다면 태그로 필터링 하지 않음", example = "[1, 2, 3]", nullable = true, defaultValue = "[]")
    val tag: List<Int> = emptyList(),
    @Schema(description = "가져올 게시글 갯수", nullable = true, defaultValue = "10")
    @field:Positive(message = "0보다 큰 수를 입력해주세요")
    val take: Int? = 10,
    @Schema(description = "가져올 게시글 시작 Id(이 Id보다 작은 Id의 게시글들을 가져옴), null이면 가장 최신데이터부턱 가져옴", nullable = true, defaultValue = "null")
    @field:Positive(message = "0보다 큰 수를 입력해주세요")
    val startId: Long? = null,
    @Schema(description = "유저 Id(게시글 작성자 Id)로 필터링", nullable = true, defaultValue = "null")
    val userId: Long? = null
) {
    fun toGetPostListRequestDto() = GetPostListRequestDto(
        tags = this.tag,
        pageSize = this.take ?: 10,
        startPostId = this.startId,
        targetUserId = this.userId
    )
}
