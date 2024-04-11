package com.adevspoon.api.board.service

import com.adevspoon.api.board.dto.response.BoardTagResponse
import com.adevspoon.api.common.annotation.ApplicationService
import com.adevspoon.domain.board.service.BoardTagDomainService

@ApplicationService
class BoardTagService(
    private val boardTagDomainService: BoardTagDomainService
) {
    fun getBoardTagResponses() : List<BoardTagResponse>{
        return boardTagDomainService.getBoardTags().map { entity -> BoardTagResponse.from(entity) }
    }
}
