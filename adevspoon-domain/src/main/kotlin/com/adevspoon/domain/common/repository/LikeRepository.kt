package com.adevspoon.domain.common.repository

import com.adevspoon.domain.common.entity.LikeEntity
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.data.jpa.repository.Query

interface LikeRepository : JpaRepository<LikeEntity, Long> {
    @Query("SELECT EXISTS (SELECT 1 FROM LikeEntity l WHERE l.user.id = :userId AND l.boardPostId = :boardPostId)")
    fun exitsByUserIdAndBoardPostId(userId: Long, boardPostId: Long): Boolean
}