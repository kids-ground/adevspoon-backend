package com.adevspoon.domain.common.repository

import com.adevspoon.domain.common.entity.LikeEntity
import com.adevspoon.domain.member.domain.UserEntity
import com.adevspoon.domain.techQuestion.domain.AnswerEntity
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.data.jpa.repository.JpaSpecificationExecutor
import org.springframework.data.jpa.repository.Modifying
import org.springframework.data.jpa.repository.Query

interface LikeRepository : JpaRepository<LikeEntity, Long>, JpaSpecificationExecutor<LikeEntity>, LikeRepositoryCustom {
    @Query("SELECT EXISTS (SELECT 1 FROM LikeEntity l WHERE l.user.id = :userId AND l.boardPostId = :boardPostId)")
    fun exitsByUserIdAndBoardPostId(userId: Long, boardPostId: Long): Boolean

    fun findByUserAndAnswer(user: UserEntity, answer: AnswerEntity): LikeEntity?

    fun findByUserAndBoardPostId(user: UserEntity, boardPostId: Long): LikeEntity?

    fun findByUserAndBoardCommentId(user: UserEntity, boardCommentId: Long): LikeEntity?

    @Query("SELECT l.boardPostId FROM LikeEntity l WHERE l.user.id = :loginUserId AND l.boardPostId In :boardPostIds")
    fun findLikedPostIdsByUser(loginUserId: Long, boardPostIds: List<Long>): List<Long>


    @Query("SELECT l.boardCommentId FROM LikeEntity l WHERE l.user.id = :loginUserId AND l.boardCommentId In :boardCommentIds")
    fun findLikedCommentIdsByUser(loginUserId: Long, boardCommentIds: List<Long>): List<Long>

    @Query("SELECT l.answer.id FROM LikeEntity l WHERE l.user.id = :userId AND l.answer.id In :answerIds")
    fun findAllIdsByUserIdAndAnswerIds(userId: Long, answerIds: List<Long>): List<Long>


    @Modifying(clearAutomatically = true)
    fun deleteAllByUserAndAnswer(user: UserEntity, answer: AnswerEntity)

    fun deleteAllByUserAndBoardPostId(user: UserEntity, boardPostId: Long)

    fun deleteAllByUserAndBoardCommentId(user: UserEntity, boardCommentId: Long)
}