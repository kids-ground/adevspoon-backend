package com.adevspoon.api.board.service

import com.adevspoon.api.board.dto.request.RegisterBoardPostRequest
import com.adevspoon.api.board.dto.response.BoardInfoResponse
import com.adevspoon.api.common.annotation.ApplicationService
import com.adevspoon.domain.board.service.BoardPostDomainService

@ApplicationService
class BoardService(
    private val boardPostDomainService: BoardPostDomainService,
) {
    fun registerBoardPost(userId: Long, request: RegisterBoardPostRequest): BoardInfoResponse {
        val boardPost = boardPostDomainService.registerBoardPost(userId, request.tagId, request.title, request.content)
        return BoardInfoResponse.from(boardPost)
    }

    fun getBoardPost(userId: Long, postId: Long): BoardInfoResponse {
        val boardPost = boardPostDomainService.getBoardPost(userId, postId)
        return BoardInfoResponse.from(boardPost)
    }

}