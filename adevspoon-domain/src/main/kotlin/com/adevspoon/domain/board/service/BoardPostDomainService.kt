package com.adevspoon.domain.board.service

import com.adevspoon.domain.board.domain.BoardPostEntity
import com.adevspoon.domain.board.dto.response.BoardPost
import com.adevspoon.domain.board.exception.BoardPostNotFoundException
import com.adevspoon.domain.board.exception.BoardTageNotFoundException
import com.adevspoon.domain.board.repository.BoardPostRepository
import com.adevspoon.domain.board.repository.BoardTagRepository
import com.adevspoon.domain.common.annotation.DomainService
import com.adevspoon.domain.member.service.MemberDomainService
import org.springframework.data.repository.findByIdOrNull
import org.springframework.transaction.annotation.Transactional

@DomainService
class BoardPostDomainService(
    val memberDomainService: MemberDomainService,
    val boardPostRepository: BoardPostRepository,
    val boardTagRepository: BoardTagRepository,
) {
    @Transactional
    fun registerBoardPost(userId: Long, tagId: Int, title: String, content: String): BoardPost {
        val user = memberDomainService.getUserEntity(userId)
        val memberProfile = memberDomainService.getMemberProfile(userId)
        val tag = boardTagRepository.findByIdOrNull(tagId) ?: throw BoardTageNotFoundException()
        val boardPost = BoardPostEntity(user = user, tag = tag, title = title, content = content, likeCount = 0, commentCount = 0, viewCount = 0)
        val savedBoardPost = boardPostRepository.save(boardPost)
        return BoardPost.from(savedBoardPost, memberProfile)
    }

    @Transactional
    fun getBoardPost(userId: Long, postId: Long): BoardPost {
        val boardPost = boardPostRepository.findByIdOrNull(postId) ?: throw BoardPostNotFoundException()
        boardPost.increaseViewCount()
        boardPostRepository.save(boardPost)
        val memberProfile = memberDomainService.getMemberProfile(userId)
        return BoardPost.from(boardPost, memberProfile)
    }

}
