package com.adevspoon.domain.common.service

import com.adevspoon.domain.board.domain.BoardCommentEntity
import com.adevspoon.domain.board.domain.BoardPostEntity
import com.adevspoon.domain.common.annotation.DomainService
import com.adevspoon.domain.common.entity.LikeEntity
import com.adevspoon.domain.common.repository.LikeRepository
import com.adevspoon.domain.member.domain.UserEntity
import com.adevspoon.domain.techQuestion.domain.AnswerEntity
import org.springframework.transaction.annotation.Transactional

@DomainService
class LikeDomainService(
    private val likeRepository: LikeRepository
) {
    @Transactional
    fun isUserLikedPost(userId: Long, boardPostId: Long): Boolean {
        return likeRepository.exitsByUserIdAndBoardPostId(userId, boardPostId)
    }

    @Transactional
    fun isUserLikedAnswer(user: UserEntity, answer: AnswerEntity): Boolean {
        return likeRepository.findByUserAndAnswer(user, answer) != null
    }

    @Transactional(readOnly = true)
    fun getLikedPostIdsByUser(loginUserId: Long, boardPostIds: List<Long>): List<Long> {
        return likeRepository.findLikedPostIdsByUser(loginUserId, boardPostIds)
    }

    @Transactional
    fun toggleAnswerLike(answer: AnswerEntity, user: UserEntity, isLike: Boolean) {
        // FIXME : LikeEntity 나누기(Board, Comment, Answer), 각각 Unique Key 설정 필요 (현재 동시성을 못다룸 - 클라이언트에서만 처리)
        val likeEntity = likeRepository.findByUserAndAnswer(user, answer)
        if (!isLike && likeEntity != null) {
            answer.decreaseLikeCount()
            likeRepository.deleteAllByUserAndAnswer(user, answer)
        } else if (isLike && likeEntity == null) {
            likeRepository.save(
                LikeEntity(user = user, postType = "answer", answer = answer)
            )
            answer.increaseLikeCount()
        }
    }

    @Transactional
    fun togglePostLike(post: BoardPostEntity, user: UserEntity, isLike: Boolean) {
        val likeEntity = likeRepository.findByUserAndBoardPostId(user, post.id)
        if (!isLike && likeEntity != null) {
            post.decreaseLikeCount()
            likeRepository.deleteAllByUserAndBoardPostId(user, post.id)
        } else if (isLike && likeEntity == null) {
            likeRepository.save(
                LikeEntity(user = user, postType = "board_post", boardPostId = post.id)
            )
            post.increaseLikeCount()
        }
    }

    @Transactional
    fun toggleCommentLike(comment: BoardCommentEntity, user: UserEntity, isLike: Boolean) {
        val likeEntity = likeRepository.findByUserAndBoardCommentId(user, comment.id)
        if (!isLike && likeEntity != null) {
            comment.decreaseLikeCount()
            likeRepository.deleteAllByUserAndBoardCommentId(user, comment.id)
        } else if (isLike && likeEntity == null) {
            likeRepository.save(
                LikeEntity(user = user, postType = "board_comment", boardCommentId = comment.id)
            )
            comment.increaseLikeCount()
        }
    }
}
