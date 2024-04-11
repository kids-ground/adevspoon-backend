package com.adevspoon.domain.board.service

import com.adevspoon.domain.board.domain.BoardTagEntity
import com.adevspoon.domain.board.repository.BoardTagRepository
import com.adevspoon.domain.common.annotation.DomainService
import org.springframework.transaction.annotation.Transactional

@DomainService
class BoardTagDomainService(
    private val boardTagRepository: BoardTagRepository
) {
    @Transactional(readOnly = true)
    fun getBoardTags(): List<BoardTagEntity> {
        return boardTagRepository.findAll()
    }
}