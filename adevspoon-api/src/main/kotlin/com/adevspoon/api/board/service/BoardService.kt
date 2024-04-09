package com.adevspoon.api.board.service

import com.adevspoon.api.board.dto.request.RegisterBoardPostRequest
import com.adevspoon.api.board.dto.response.BoardInfoResponse
import com.adevspoon.api.board.dto.response.BoardTagResponse
import com.adevspoon.api.common.annotation.ApplicationService
import com.adevspoon.domain.member.service.MemberDomainService
import com.adevspoon.domain.post.board.service.BoardPostDomainService

@ApplicationService
class BoardService (
        private val memberDomainService: MemberDomainService,
        private val boardPostDomainService: BoardPostDomainService,
){
    fun registerBoardPost(userId: Long, request: RegisterBoardPostRequest): BoardInfoResponse {
        val memberProfile = memberDomainService.getMemberProfile(userId)
        val boardPost = boardPostDomainService.registerBoardPost(userId, request.tagId, request.title, request.content)
        val boardTag = BoardTagResponse.from(boardPost.tag)
        return BoardInfoResponse.from(boardPost, boardTag, memberProfile);
    }

}