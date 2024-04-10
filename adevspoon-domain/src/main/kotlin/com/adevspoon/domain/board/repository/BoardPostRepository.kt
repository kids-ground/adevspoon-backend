package com.adevspoon.domain.board.repository

import com.adevspoon.domain.board.domain.BoardPostEntity
import org.springframework.data.jpa.repository.JpaRepository

interface BoardPostRepository : JpaRepository<BoardPostEntity, Long>
