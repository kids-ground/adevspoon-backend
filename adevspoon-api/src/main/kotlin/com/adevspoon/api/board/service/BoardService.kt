package com.adevspoon.api.board.service

import com.adevspoon.api.board.dto.request.RegisterBoardPostRequest
import com.adevspoon.api.board.dto.response.BoardInfoResponse
import com.adevspoon.api.board.dto.response.BoardTagResponse
import com.adevspoon.api.common.annotation.ApplicationService
import com.adevspoon.api.member.dto.response.MemberProfileResponse
import com.adevspoon.domain.board.service.BoardPostDomainService

@ApplicationService
class BoardService(
    private val boardPostDomainService: BoardPostDomainService,
) {
    fun registerBoardPost(userId: Long, request: RegisterBoardPostRequest): BoardInfoResponse {
        val boardPost = boardPostDomainService.registerBoardPost(userId, request.tagId, request.title, request.content)
        val boardTag = BoardTagResponse.from(boardPost.tag)
        val memberProfileResponse = MemberProfileResponse.from(boardPost.user)
        return BoardInfoResponse.from(boardPost, boardTag, memberProfileResponse)
    }

}