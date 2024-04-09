package com.adevspoon.domain.post.board.repository

import com.adevspoon.domain.post.board.domain.BoardTagEntity
import org.springframework.data.jpa.repository.JpaRepository

interface BoardTagRepository : JpaRepository<BoardTagEntity, Int> {
}