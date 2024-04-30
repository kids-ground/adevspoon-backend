package com.adevspoon.api.board.service


import com.adevspoon.api.board.dto.request.*
import com.adevspoon.api.board.dto.response.BoardInfoResponse
import com.adevspoon.api.board.dto.response.BoardListResponse
import com.adevspoon.api.common.annotation.ApplicationService
import com.adevspoon.domain.board.service.BoardPostDomainService

@ApplicationService
class BoardService(
    private val boardPostDomainService: BoardPostDomainService,
) {
    fun registerBoardPost(request: RegisterBoardPostRequest, userId: Long): BoardInfoResponse {
        val boardPost = boardPostDomainService.registerBoardPost(
            request.toRegisterPostRequestDto(), userId)
        return BoardInfoResponse.from(boardPost)
    }

    fun getBoardPost(postId: Long, userId: Long): BoardInfoResponse {
        val boardPost = boardPostDomainService.getBoardPost(postId, userId)
        return BoardInfoResponse.from(boardPost)
    }

    fun getBoardPostsByTags(request: BoardListRequest, loginUserId: Long): BoardListResponse {
        val pageWithCursor = boardPostDomainService.getBoardPostsWithCriteria(request.toGetPostListRequestDto(), loginUserId)
        return BoardListResponse.from(pageWithCursor.data, pageWithCursor.nextCursorId)
    }

    fun updateBoardPost(request: UpdateBoardPostRequest, userId: Long): BoardInfoResponse {
        val boardPost = boardPostDomainService.updateBoardPost(request.toUpdatePostRequestDto(), userId)
        return BoardInfoResponse.from(boardPost)
    }

    fun deleteBoardById(request: BoardDeleteRequest, userId: Long): String {
        boardPostDomainService.deleteById(request.postId, userId)
        return "Successfully delete. postId:${request.postId}"
    }

    fun likeBoard(request: LikeBoardContentRequest, userId: Long): String {
        val likeRequest = request.toLikeStateRequest()
        val type = likeRequest.type
        val contentId = likeRequest.contentId

        boardPostDomainService.toggleBoardLike(likeRequest, userId)
        return if (likeRequest.like) "type:${type} contentId:${contentId} 좋아요 완료." else "type:${type} contentId:${contentId} 취소 완료."
    }

    fun report(request: ReportBoardContentRequest, userId: Long): String {
        val report = boardPostDomainService.report(request.toCreateReportRequest(), userId)
        return "Successfully report. id=${report.id}. type:${report.postType}, reason:${report.reason}"
    }
}