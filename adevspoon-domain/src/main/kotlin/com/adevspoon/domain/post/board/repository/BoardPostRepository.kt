package com.adevspoon.domain.post.board.repository

import com.adevspoon.domain.post.board.domain.BoardPostEntity
import org.springframework.data.jpa.repository.JpaRepository

interface BoardPostRepository : JpaRepository<BoardPostEntity, Long>
