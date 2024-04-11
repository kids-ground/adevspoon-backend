package com.adevspoon.domain.board.service

import com.adevspoon.domain.board.domain.BoardTagEntity
import com.adevspoon.domain.board.repository.BoardTagRepository
import com.adevspoon.domain.common.annotation.DomainService

@DomainService
class BoardTagDomainService(
    private val boardTagRepository: BoardTagRepository
) {
    fun getBoardTags(): List<BoardTagEntity> {
        return boardTagRepository.findAll()
    }
}