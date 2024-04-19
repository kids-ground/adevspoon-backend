package com.adevspoon.domain.common.repository

import com.adevspoon.domain.common.entity.LikeEntity
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.data.jpa.repository.Query

interface LikeRepository : JpaRepository<LikeEntity, Long> {
    @Query("SELECT EXISTS (SELECT 1 FROM LikeEntity l WHERE l.user.id = :userId AND l.boardPostId = :boardPostId)")
    fun exitsByUserIdAndBoardPostId(userId: Long, boardPostId: Long): Boolean

    @Query("SELECT l.boardPostId FROM LikeEntity l WHERE l.user.id = :loginUserId AND l.boardPostId In :boardPostIds")
    fun findLikedPostIdsByUser(loginUserId: Long, boardPostIds: List<Long>): List<Long>
}