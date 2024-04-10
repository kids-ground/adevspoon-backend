package com.adevspoon.domain.board.service

import com.adevspoon.domain.common.annotation.DomainService
import com.adevspoon.domain.member.exception.MemberNotFoundException
import com.adevspoon.domain.member.repository.UserRepository
import com.adevspoon.domain.board.domain.BoardPostEntity
import com.adevspoon.domain.board.dto.response.BoardPost
import com.adevspoon.domain.board.exception.BoardTageNotFoundException
import com.adevspoon.domain.board.repository.BoardPostRepository
import com.adevspoon.domain.board.repository.BoardTagRepository
import com.adevspoon.domain.member.service.MemberDomainService
import org.springframework.data.repository.findByIdOrNull
import org.springframework.transaction.annotation.Transactional

@DomainService
class BoardPostDomainService(
    val memberDomainService: MemberDomainService,
    val userRepository: UserRepository,
    val boardPostRepository: BoardPostRepository,
    val boardTagRepository: BoardTagRepository,
) {
    @Transactional
    fun registerBoardPost(userId: Long, tagId: Int, title: String, content: String): BoardPost {
        // FIXME: userId로 user를 호출하는 방안 검토
        val user = userRepository.findByIdOrNull(userId) ?: throw MemberNotFoundException()
        val memberProfile = memberDomainService.getMemberProfile(userId)
        val tag = boardTagRepository.findByIdOrNull(tagId) ?: throw BoardTageNotFoundException()
        val boardPost = BoardPostEntity(user = user, tag = tag, title = title, content = content, likeCount = 0, commentCount = 0, viewCount = 0)
        val savedBoardPost = boardPostRepository.save(boardPost)
        return BoardPost.from(savedBoardPost, memberProfile)
    }

}
