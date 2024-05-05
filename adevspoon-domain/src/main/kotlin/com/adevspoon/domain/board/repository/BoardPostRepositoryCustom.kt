package com.adevspoon.domain.board.repository

import com.adevspoon.domain.board.domain.BoardPostEntity
import org.springframework.data.domain.Pageable
import org.springframework.data.domain.Slice

interface BoardPostRepositoryCustom {
    fun findByTagsAndUserIdWithCursor(tags: List<Int>?, startPostId: Long?, targetUserId: Long?, pageable: Pageable): Slice<BoardPostEntity>
}