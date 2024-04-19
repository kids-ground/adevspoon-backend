package com.adevspoon.api.board.dto.response

import com.adevspoon.domain.board.dto.response.BoardPost
import org.springframework.web.servlet.support.ServletUriComponentsBuilder

data class BoardListResponse(
    val list: List<BoardInfoResponse>,
    val next: String?,
) {
    companion object {
        fun from(boardList: List<BoardPost>, nextCursorId: Long?): BoardListResponse {
            val nextUrl = if (nextCursorId != null) {
                ServletUriComponentsBuilder.fromCurrentRequest()
                    .replaceQueryParam("startId", nextCursorId)
                    .build()
                    .toUriString()
            } else null
            return BoardListResponse(
                list = boardList.map(BoardInfoResponse::from).toList(),
                next = nextUrl
            )
        }
    }
}