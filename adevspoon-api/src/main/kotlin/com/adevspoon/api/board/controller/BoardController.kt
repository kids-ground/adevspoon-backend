package com.adevspoon.api.board.controller

import com.adevspoon.api.board.dto.request.*
import com.adevspoon.api.board.dto.response.BoardInfoResponse
import com.adevspoon.api.board.dto.response.BoardListResponse
import com.adevspoon.api.board.dto.response.BoardTagResponse
import com.adevspoon.api.board.service.BoardService
import com.adevspoon.api.board.service.BoardTagService
import com.adevspoon.api.common.annotation.RequestUser
import com.adevspoon.api.common.dto.RequestUserInfo
import com.adevspoon.api.config.swagger.SWAGGER_TAG_BOARD
import io.swagger.v3.oas.annotations.Operation
import io.swagger.v3.oas.annotations.tags.Tag
import jakarta.validation.Valid
import org.springframework.web.bind.annotation.*

@RestController
@RequestMapping("/board")
@Tag(name = SWAGGER_TAG_BOARD)
class BoardController(
    private val boardTagService: BoardTagService,
    private val boardService: BoardService
) {
    @Operation(summary = "게시판 전체 태그 리스트 조회")
    @GetMapping("/tag")
    fun getBoardTagList(): List<BoardTagResponse> {
        return boardTagService.getBoardTagResponses()
    }

    @Operation(summary = "게시글 등록")
    @PostMapping("/post")
    fun registerBoardPost(
        @RequestUser requestUser: RequestUserInfo,
        @RequestBody @Valid request: RegisterBoardPostRequest,
    ): BoardInfoResponse {
        return boardService.registerBoardPost(request, requestUser.userId)
    }

    @Operation(summary = "게시글 id 기반 게시글 조회")
    @GetMapping("/post/{postId}")
    fun getBoardPost(
        @RequestUser requestUser: RequestUserInfo,
        @PathVariable postId: Long,
    ): BoardInfoResponse {
        return boardService.getBoardPost(postId, requestUser.userId)
    }

    @Operation(summary = "게시글 리스트 조회")
    @GetMapping("/post")
    fun getBoardPostList(
        @RequestUser requestUser: RequestUserInfo,
        @Valid request: BoardListRequest,
    ): BoardListResponse {
        return boardService.getBoardPostsByTags(request, requestUser.userId)
    }

    @Operation(summary = "게시글 수정")
    @PatchMapping("/post")
    fun updateBoardPost(
        @RequestUser requestUser: RequestUserInfo,
        @RequestBody @Valid request: UpdateBoardPostRequest,
    ): BoardInfoResponse {
        return boardService.updateBoardPost(request, requestUser.userId)

    }

    @Operation(summary = "게시글 삭제")
    @DeleteMapping("/post")
    fun deleteBoardPost(
        @RequestUser requestUser: RequestUserInfo,
        @RequestBody @Valid request: BoardDeleteRequest,
    ): String {
        TODO("""
            게시판 글 삭제
        """.trimIndent())
    }

    @Operation(summary = "게시글 좋아요")
    @PostMapping("/like")
    fun likeBoardPost(
        @RequestUser requestUser: RequestUserInfo,
        @RequestBody @Valid request: LikeBoardContentRequest
    ): String {
        TODO("""
            게시판 글 좋아요
            - post, comment 둘 다 있음
        """.trimIndent())
    }

    @Operation(summary = "게시글 신고")
    @PostMapping("/report")
    fun reportBoardPost(
        @RequestUser requestUser: RequestUserInfo,
        @RequestBody @Valid request: ReportBoardContentRequest
    ): String {
        TODO("""
            게시판 글 신고
        """.trimIndent())
    }
}