package com.adevspoon.domain.board.service

import com.adevspoon.domain.board.domain.BoardCommentEntity
import com.adevspoon.domain.board.dto.request.RegisterCommentRequestDto
import com.adevspoon.domain.board.dto.response.BoardComment
import com.adevspoon.domain.board.exception.BoardCommentNotFoundException
import com.adevspoon.domain.board.exception.BoardPostCommentException
import com.adevspoon.domain.board.exception.BoardPostNotFoundException
import com.adevspoon.domain.board.repository.BoardCommentRepository
import com.adevspoon.domain.board.repository.BoardPostRepository
import com.adevspoon.domain.common.annotation.DomainService
import com.adevspoon.domain.common.service.LikeDomainService
import com.adevspoon.domain.member.dto.response.MemberProfile
import com.adevspoon.domain.member.exception.MemberNotFoundException
import com.adevspoon.domain.member.repository.BadgeRepository
import com.adevspoon.domain.member.repository.UserRepository
import com.adevspoon.domain.member.service.MemberDomainService
import org.springframework.data.repository.findByIdOrNull
import org.springframework.transaction.annotation.Transactional

@DomainService
class BoardCommentDomainService(
    private val boardCommentRepository: BoardCommentRepository,
    private val boardPostRepository: BoardPostRepository,
    private val userRepository: UserRepository,
    private val badgeRepository: BadgeRepository,
    private val memberDomainService: MemberDomainService,
    private val likeDomainService: LikeDomainService
) {
    @Transactional
    fun register(request: RegisterCommentRequestDto, userId: Long): BoardComment {
        val user = getUserEntity(userId)
        val boardPost = getBoardPostEntity(request.postId)
        val comment = BoardCommentEntity(user = user, post = boardPost, content = request.content, likeCount = 0)
        boardCommentRepository.save(comment)
        return BoardComment.from(
            comment = comment,
            memberProfile = memberDomainService.getOtherMemberProfile(userId),
            isLike = false,
            isMine = true
        )
    }

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

    @Transactional
    fun deleteById(commentId: Long, userId: Long) {
        val boardComment = getBoardCommentEntity(commentId)
        validateCommentOwnership(boardComment, userId)
        boardCommentRepository.deleteById(commentId)
    }

    private fun validateCommentOwnership(boardComment: BoardCommentEntity, userId: Long) {
        if (boardComment.user.id != userId) {
            throw BoardPostCommentException(commentOwnerId = boardComment.user.id.toString(), loginUserId = userId.toString())
        }
    }

    private fun getLikedCommentsByUser(loginUserId: Long, commentsIds: List<Long>): Set<Long> {
        return likeDomainService.getLikedCommentIdsByUser(loginUserId, commentsIds).toSet()
    }

    private fun getBoardPostEntity(postId: Long) =
        boardPostRepository.findByIdOrNull(postId) ?: throw BoardPostNotFoundException(postId.toString())

    private fun getBoardCommentEntity(commentId: Long): BoardCommentEntity =
        boardCommentRepository.findByIdOrNull(commentId) ?: throw BoardCommentNotFoundException(commentId.toString())

    private fun getUserEntity(userId: Long) =
        userRepository.findByIdOrNull(userId) ?: throw MemberNotFoundException()
}
