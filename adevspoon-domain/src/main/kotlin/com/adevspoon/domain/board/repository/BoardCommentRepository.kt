package com.adevspoon.domain.board.repository

import com.adevspoon.domain.board.domain.BoardCommentEntity
import com.adevspoon.domain.board.domain.BoardPostEntity
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.data.jpa.repository.Query

interface BoardCommentRepository : JpaRepository<BoardCommentEntity, Long> {

    @Query("SELECT c FROM BoardCommentEntity c WHERE c.post = :boardPost ORDER BY c.id DESC")
    fun findAllByBoardPost(boardPost: BoardPostEntity) : List<BoardCommentEntity>
}
