package com.adevspoon.domain.board.service

import com.adevspoon.domain.common.annotation.DomainService
import com.adevspoon.domain.member.exception.MemberNotFoundException
import com.adevspoon.domain.member.repository.UserRepository
import com.adevspoon.domain.board.domain.BoardPostEntity
import com.adevspoon.domain.board.exception.BoardTageNotFoundException
import com.adevspoon.domain.board.repository.BoardPostRepository
import com.adevspoon.domain.board.repository.BoardTagRepository
import org.springframework.data.repository.findByIdOrNull

@DomainService
class BoardPostDomainService (
        val userRepository: UserRepository,
        val boardPostRepository: BoardPostRepository,
        val boardTagRepository: BoardTagRepository
){
    fun registerBoardPost(userId: Long, tagId: Int, title: String, content: String): BoardPostEntity {
        val user = userRepository.findByIdOrNull(userId) ?: throw MemberNotFoundException()
        val tag = boardTagRepository.findByIdOrNull(tagId) ?: throw BoardTageNotFoundException()
        var boardPost = BoardPostEntity(user = user, tag = tag, title = title, content = content
                , likeCount = 0, commentCount = 0, viewCount = 0)
        return boardPostRepository.save(boardPost)
    }

}
