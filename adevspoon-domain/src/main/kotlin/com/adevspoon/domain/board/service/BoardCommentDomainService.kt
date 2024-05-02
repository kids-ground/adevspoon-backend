package com.adevspoon.domain.board.service

import com.adevspoon.domain.board.domain.BoardCommentEntity
import com.adevspoon.domain.board.dto.response.BoardComment
import com.adevspoon.domain.board.exception.BoardCommentNotFoundException
import com.adevspoon.domain.board.exception.BoardPostNotFoundException
import com.adevspoon.domain.board.repository.BoardCommentRepository
import com.adevspoon.domain.board.repository.BoardPostRepository
import com.adevspoon.domain.common.annotation.DomainService
import com.adevspoon.domain.common.service.LikeDomainService
import com.adevspoon.domain.member.dto.response.MemberProfile
import com.adevspoon.domain.member.repository.BadgeRepository
import org.springframework.data.repository.findByIdOrNull
import org.springframework.transaction.annotation.Transactional

@DomainService
class BoardCommentDomainService(
    private val boardCommentRepository: BoardCommentRepository,
    private val boardPostRepository: BoardPostRepository,
    private val badgeRepository: BadgeRepository,
    private val likeDomainService: LikeDomainService
) {
    @Transactional(readOnly = true)
    fun getCommentsByPostId(postId: Long, userId: Long): List<BoardComment> {
        val boardPost = getBoardPostEntity(postId)
        val commentAndAuthors = boardCommentRepository.findCommentAuthorsByPost(boardPost)
        val allBadges = badgeRepository.findAll()
        val commentIds = commentAndAuthors.map { it.comment.id }
        val likedCommentIds = getLikedCommentsByUser(userId, commentIds)
        return commentAndAuthors.map { commentAndAuthor ->
            BoardComment.from(
                comment = commentAndAuthor.comment,
                memberProfile = MemberProfile.from(
                    user = commentAndAuthor.user,
                    null,
                    allBadges.find { badge -> badge.id == commentAndAuthor.user.representativeBadge}),
                isLike = likedCommentIds.contains(commentAndAuthor.comment.id),
                isMine = commentAndAuthor.user.id == userId
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
