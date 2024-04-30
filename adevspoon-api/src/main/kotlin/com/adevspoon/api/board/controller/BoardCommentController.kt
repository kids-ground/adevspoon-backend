package com.adevspoon.api.board.controller

import com.adevspoon.api.board.dto.request.BoardCommentDeleteRequest
import com.adevspoon.api.board.dto.request.RegisterBoardCommentRequest
import com.adevspoon.api.board.dto.response.BoardCommentListResponse
import com.adevspoon.api.board.dto.response.BoardCommentResponse
import com.adevspoon.api.board.service.BoardCommentService
import com.adevspoon.api.common.annotation.RequestUser
import com.adevspoon.api.common.dto.RequestUserInfo
import com.adevspoon.api.config.swagger.SWAGGER_TAG_BOARD_COMMENT
import io.swagger.v3.oas.annotations.Operation
import io.swagger.v3.oas.annotations.tags.Tag
import jakarta.validation.Valid
import org.springframework.web.bind.annotation.*

@RestController
@RequestMapping("/board/comment")
@Tag(name = SWAGGER_TAG_BOARD_COMMENT)
class BoardCommentController (
    private val boardCommentService : BoardCommentService
){
    @Operation(summary = "게시판 댓글 리스트 조회", description = "게시글 id를 쿼리로 받아 게시판 댓글 리스트 조회")
    @GetMapping
    fun getBoardCommentList(
        @RequestUser user: RequestUserInfo,
        @RequestParam postId: Long,
    ): BoardCommentListResponse {
        return boardCommentService.getComments(postId, user.userId)
    }

    @Operation(summary = "게시글의 댓글 등록")
    @PostMapping
    fun registerBoardComment(
        @RequestUser user: RequestUserInfo,
        @RequestBody @Valid request: RegisterBoardCommentRequest
    ): BoardCommentResponse {
        TODO("""
            게시판 댓글 등록
        """.trimIndent())
    }

    @Operation(summary = "게시글의 댓글 삭제")
    @DeleteMapping
    fun deleteBoardComment(
        @RequestUser user: RequestUserInfo,
        @RequestBody @Valid request: BoardCommentDeleteRequest,
    ): String {
        TODO("""
            게시판 댓글 삭제
        """.trimIndent())
    }
}