package com.adevspoon.domain.board.service

import com.adevspoon.domain.board.domain.BoardCommentEntity
import com.adevspoon.domain.board.dto.response.BoardComment
import com.adevspoon.domain.board.exception.BoardCommentNotFoundException
import com.adevspoon.domain.board.exception.BoardPostNotFoundException
import com.adevspoon.domain.board.repository.BoardCommentRepository
import com.adevspoon.domain.board.repository.BoardPostRepository
import com.adevspoon.domain.common.annotation.DomainService
import com.adevspoon.domain.common.service.LikeDomainService
import com.adevspoon.domain.member.service.MemberDomainService
import org.springframework.data.repository.findByIdOrNull
import org.springframework.transaction.annotation.Transactional

@DomainService
class BoardCommentDomainService(
    private val boardCommentRepository: BoardCommentRepository,
    private val boardPostRepository: BoardPostRepository,
    private val memberDomainService: MemberDomainService,
    private val likeDomainService: LikeDomainService
) {
    @Transactional(readOnly = true)
    fun getCommentsByPostId(postId: Long, userId: Long): List<BoardComment> {
        val boardPost = getBoardPostEntity(postId)
        val comments = boardCommentRepository.findAllByBoardPost(boardPost)
        val likedCommentIds = getLikedCommentsByUser(userId, comments.map { it.id }.toList())
        return comments.map { comment ->
            BoardComment.from(
                comment = comment,
                memberProfile = memberDomainService.getMemberProfile(comment.user.id),
                isLike = likedCommentIds.contains(comment.id),
                isMine = comment.user.id == userId
            )
        }
    }

    private fun getLikedCommentsByUser(loginUserId: Long, commentsIds: List<Long>): Set<Long> {
        return likeDomainService.getLikedCommentIdsByUser(loginUserId, commentsIds).toSet()
    }

    private fun getBoardPostEntity(postId: Long) =
        boardPostRepository.findByIdOrNull(postId) ?: throw BoardPostNotFoundException(postId.toString())

    private fun getBoardCommentEntity(commentId: Long): BoardCommentEntity =
        boardCommentRepository.findByIdOrNull(commentId) ?: throw BoardCommentNotFoundException(commentId.toString())
}
