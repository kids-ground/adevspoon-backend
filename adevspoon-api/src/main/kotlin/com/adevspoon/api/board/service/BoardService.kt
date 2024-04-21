package com.adevspoon.api.board.service

import com.adevspoon.api.board.dto.request.BoardListRequest
import com.adevspoon.api.board.dto.request.RegisterBoardPostRequest
import com.adevspoon.api.board.dto.request.UpdateBoardPostRequest
import com.adevspoon.api.board.dto.response.BoardInfoResponse
import com.adevspoon.api.board.dto.response.BoardListResponse
import com.adevspoon.api.common.annotation.ApplicationService
import com.adevspoon.domain.board.service.BoardPostDomainService

@ApplicationService
class BoardService(
    private val boardPostDomainService: BoardPostDomainService,
) {
    fun registerBoardPost(request: RegisterBoardPostRequest, userId: Long): BoardInfoResponse {
        val boardPost = boardPostDomainService.registerBoardPost(request.title, request.content, request.tagId, userId)
        return BoardInfoResponse.from(boardPost)
    }

    fun getBoardPost(postId: Long, userId: Long): BoardInfoResponse {
        val boardPost = boardPostDomainService.getBoardPost(postId, userId)
        return BoardInfoResponse.from(boardPost)
    }

    fun getBoardPostsByTags(request: BoardListRequest, loginUserId: Long) : BoardListResponse  {
        val pageWithCursor = boardPostDomainService.getBoardPostsWithCriteria(request.tag, request.take, request.startId, request.userId, loginUserId)
        return BoardListResponse.from(pageWithCursor.data, pageWithCursor.nextCursorId)
    }

    fun updateBoardPost(request: UpdateBoardPostRequest, userId: Long): BoardInfoResponse {
        val boardPost = boardPostDomainService.updateBoardPost(request.title, request.content, request.tagId, request.postId, userId)
        return BoardInfoResponse.from(boardPost)
    }
}