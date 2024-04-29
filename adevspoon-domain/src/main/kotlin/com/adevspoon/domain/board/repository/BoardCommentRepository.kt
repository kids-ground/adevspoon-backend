package com.adevspoon.domain.board.repository

import com.adevspoon.domain.board.domain.BoardCommentEntity
import org.springframework.data.jpa.repository.JpaRepository

interface BoardCommentRepository : JpaRepository<BoardCommentEntity, Long>
