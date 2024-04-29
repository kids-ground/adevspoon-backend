package com.adevspoon.domain.common.repository

import com.adevspoon.domain.common.entity.ReportEntity
import com.adevspoon.domain.member.domain.UserEntity
import com.adevspoon.domain.techQuestion.domain.AnswerEntity
import org.springframework.data.jpa.repository.JpaRepository

import org.springframework.data.jpa.repository.Query

interface ReportRepository : JpaRepository<ReportEntity, Long> {
    @Query("SELECT CASE WHEN count (r) > 0 THEN TRUE ELSE FALSE END FROM ReportEntity r " +
        "WHERE (r.postType = :postType " +
        "AND (r.postType = 'board_post' AND r.boardPostId = :contentId) " +
        "OR (r.postType = 'board_comment' AND  r.boardCommentId =: contentId))")
    fun existsByPostTypeAndContentId(postType: String, contentId: Long): Boolean
  
    fun findAllByUserAndPost(user: UserEntity, post: AnswerEntity): List<ReportEntity>
}