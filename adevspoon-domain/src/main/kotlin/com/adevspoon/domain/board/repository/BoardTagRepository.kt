package com.adevspoon.domain.board.repository

import com.adevspoon.domain.board.domain.BoardTagEntity
import org.springframework.data.jpa.repository.JpaRepository

interface BoardTagRepository : JpaRepository<BoardTagEntity, Int> {
}