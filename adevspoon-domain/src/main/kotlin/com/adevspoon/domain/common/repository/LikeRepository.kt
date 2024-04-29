package com.adevspoon.domain.common.repository

import com.adevspoon.domain.common.entity.LikeEntity
import com.adevspoon.domain.member.domain.UserEntity
import com.adevspoon.domain.techQuestion.domain.AnswerEntity
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.data.jpa.repository.JpaSpecificationExecutor
import org.springframework.data.jpa.repository.Query

interface LikeRepository : JpaRepository<LikeEntity, Long>, JpaSpecificationExecutor<LikeEntity> {
    @Query("SELECT EXISTS (SELECT 1 FROM LikeEntity l WHERE l.user.id = :userId AND l.boardPostId = :boardPostId)")
    fun exitsByUserIdAndBoardPostId(userId: Long, boardPostId: Long): Boolean

    fun findByUserAndAnswer(user: UserEntity, answer: AnswerEntity): LikeEntity?

    @Query("SELECT l.boardPostId FROM LikeEntity l WHERE l.user.id = :loginUserId AND l.boardPostId In :boardPostIds")
    fun findLikedPostIdsByUser(loginUserId: Long, boardPostIds: List<Long>): List<Long>

    @Query("SELECT  l FROM  LikeEntity l " +
        "WHERE l.user.id = :userId " +
        "AND ((:type = 'board_post' AND l.boardPostId = :contentId) " +
        "OR (:type = 'board_comment' AND l.boardCommentId = :contentId))")
    fun findByTypeAndUserId(type: String, userId: Long, contentId: Long): LikeEntity?
}