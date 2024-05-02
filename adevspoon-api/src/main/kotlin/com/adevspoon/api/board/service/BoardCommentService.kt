package com.adevspoon.api.board.service

import com.adevspoon.api.board.dto.response.BoardCommentListResponse
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

}
