package com.adevspoon.api.board.service

import com.adevspoon.api.board.dto.request.BoardCommentDeleteRequest
import com.adevspoon.api.board.dto.request.RegisterBoardCommentRequest
import com.adevspoon.api.board.dto.response.BoardCommentListResponse
import com.adevspoon.api.board.dto.response.BoardCommentResponse
import com.adevspoon.api.common.annotation.ApplicationService
import com.adevspoon.domain.board.service.BoardCommentDomainService

@ApplicationService
class BoardCommentService (
    private val boardCommentDomainService : BoardCommentDomainService
){
    fun getComments(postId: Long, userId: Long): BoardCommentListResponse {
        val comments = boardCommentDomainService.getCommentsByPostId(postId, userId)
        return BoardCommentListResponse.from(comments)
    }

    fun registerBoardComment(request: RegisterBoardCommentRequest, userId: Long): BoardCommentResponse {
        val comment = boardCommentDomainService.register(request.toRegisterCommentRequestDto(), userId)
        return BoardCommentResponse.from(comment)
    }

    fun delete(request: BoardCommentDeleteRequest, userId: Long): String {
        boardCommentDomainService.deleteById(request.commentId, userId)
        return "Successfully delete. commentId:${request.commentId}"
    }
}
