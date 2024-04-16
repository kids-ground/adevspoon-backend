package com.adevspoon.domain.board.repository

import com.adevspoon.domain.board.domain.BoardPostEntity
import org.springframework.data.domain.Page
import org.springframework.data.domain.Pageable
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.data.jpa.repository.Query
import org.springframework.data.repository.query.Param

interface BoardPostRepository : JpaRepository<BoardPostEntity, Long> {

    @Query("SELECT bp FROM BoardPostEntity bp " +
        "WHERE (:startPostId IS NULL OR bp.id <= :startPostId) " +
        "AND (:targetUserId IS NULL OR bp.user.id = :targetUserId)"
    )
    fun findAllBoardPosts(
        @Param("startPostId") startPostId: Long?,
        @Param("targetUserId") targetUserId: Long?,
        pageable: Pageable) : Page<BoardPostEntity>

    @Query("SELECT bp FROM BoardPostEntity bp " +
        "JOIN bp.tag t " +
        "WHERE (:tags IS NULL OR t.id IN :tags) " +
        "AND (:startPostId IS NULL OR bp.id <= :startPostId) " +
        "AND (:targetUserId IS NULL OR bp.user.id = :targetUserId) "
        )
    fun findByTagsAndUserIdWitchCursor(
        @Param("tags") tags: List<Int>,
        @Param("startPostId") startPostId: Long?,
        @Param("targetUserId") targetUserId: Long?,
        pageable: Pageable) : Page<BoardPostEntity>
}
