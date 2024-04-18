package com.adevspoon.api.board.service

import com.adevspoon.api.board.dto.response.BoardInfoResponse
import com.adevspoon.api.board.dto.response.BoardListResponse
import com.adevspoon.api.common.annotation.ApplicationService
import com.adevspoon.domain.board.service.BoardPostDomainService

@ApplicationService
class BoardService(
    private val boardPostDomainService: BoardPostDomainService,
) {
    fun registerBoardPost(title: String, content: String, tagId: Int, userId: Long): BoardInfoResponse {
        val boardPost = boardPostDomainService.registerBoardPost(title, content, tagId, userId)
        return BoardInfoResponse.from(boardPost)
    }

    fun getBoardPost(postId: Long, userId: Long): BoardInfoResponse {
        val boardPost = boardPostDomainService.getBoardPost(postId, userId)
        return BoardInfoResponse.from(boardPost)
    }

    fun getBoardPostsByTags(tags: List<Int>, pageSize: Int, startPostId: Long?, targetUserId: Long?, loginUserId: Long) : BoardListResponse  {
        val pageWithCursor = boardPostDomainService.getBoardPostsWithCriteria(tags, pageSize, startPostId, targetUserId, loginUserId)
        return BoardListResponse.from(pageWithCursor.data, pageWithCursor.nextCursorId)
    }

    fun updateBoardPost(title: String?, content: String, tagId: Int, postId: Long, userId: Long): BoardInfoResponse {
        val boardPost = boardPostDomainService.updateBoardPost(title, content, tagId, postId, userId)
        return BoardInfoResponse.from(boardPost)
    }
}