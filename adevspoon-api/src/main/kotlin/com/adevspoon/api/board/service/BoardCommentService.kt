package com.adevspoon.api.board.service

import com.adevspoon.api.common.annotation.ApplicationService
import com.adevspoon.domain.board.dto.request.UpdateCommentLikeStateRequest
import com.adevspoon.domain.common.service.LikeDomainService

@ApplicationService
class BoardCommentService (
    private val likeDomainService: LikeDomainService
){
    fun toggleLike(request: UpdateCommentLikeStateRequest, userId: Long) : String{
        likeDomainService.toggleLike(request.type, request.contentId, request.like, userId)
        return "Successfully toggled comment like."
    }
}
