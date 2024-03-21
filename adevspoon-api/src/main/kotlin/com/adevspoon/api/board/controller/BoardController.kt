package com.adevspoon.api.board.controller

import com.adevspoon.api.board.dto.request.*
import com.adevspoon.api.board.dto.response.BoardInfoResponse
import com.adevspoon.api.board.dto.response.BoardListResponse
import com.adevspoon.api.board.dto.response.BoardTagResponse
import com.adevspoon.api.common.annotation.RequestUser
import com.adevspoon.api.common.dto.RequestUserInfo
import com.adevspoon.api.config.swagger.SWAGGER_TAG_BOARD
import com.adevspoon.common.dto.PlainResponse
import io.swagger.v3.oas.annotations.Operation
import io.swagger.v3.oas.annotations.tags.Tag
import jakarta.validation.Valid
import org.springframework.web.bind.annotation.*

@RestController
@RequestMapping("/board")
@Tag(name = SWAGGER_TAG_BOARD)
class BoardController {
    @Operation(summary = "게시판 전체 태그 리스트 조회")
    @GetMapping("/tag")
    fun getBoardTagList(): BoardTagResponse {
        TODO("""
            게시판 전체 태그 리스트 조회
        """.trimIndent())
    }

    @Operation(summary = "게시글 등록")
    @PostMapping("/post")
    fun registerBoardPost(
        @RequestUser user: RequestUserInfo,
        @RequestBody @Valid request: RegisterBoardPostRequest,
    ): BoardInfoResponse {
        TODO("""
            게시판 글 등록
        """.trimIndent())
    }

    @Operation(summary = "게시글 id기반 게시글 조회")
    @GetMapping("/post/{postId}")
    fun getBoardPost(
        @RequestUser user: RequestUserInfo,
        @PathVariable postId: Long,
    ): BoardInfoResponse {
        TODO("""
            게시판 글 조회
        """.trimIndent())
    }

    @Operation(summary = "게시글 리스트 조회")
    @GetMapping("/post")
    fun getBoardPostList(
        @RequestUser user: RequestUserInfo,
        @Valid request: BoardListRequest,
    ): BoardListResponse {
        TODO("""
            게시판 글 리스트 조회
        """.trimIndent())
    }

    @Operation(summary = "게시글 수정")
    @PatchMapping("/post")
    fun updateBoardPost(
        @RequestUser user: RequestUserInfo,
        @RequestBody @Valid request: UpdateBoardPostRequest,
    ): BoardInfoResponse {
        TODO("""
            게시판 글 수정
        """.trimIndent())
    }

    @Operation(summary = "게시글 삭제")
    @DeleteMapping("/post")
    fun deleteBoardPost(
        @RequestUser user: RequestUserInfo,
        @RequestBody @Valid request: BoardDeleteRequest,
    ): PlainResponse {
        TODO("""
            게시판 글 삭제
        """.trimIndent())
    }

    @Operation(summary = "게시글 좋아요")
    @PostMapping("/like")
    fun likeBoardPost(
        @RequestUser user: RequestUserInfo,
        @RequestBody @Valid request: LikeBoardContentRequest
    ): PlainResponse {
        TODO("""
            게시판 글 좋아요
            - post, comment 둘 다 있음
        """.trimIndent())
    }

    @Operation(summary = "게시글 신고")
    @PostMapping("/report")
    fun reportBoardPost(
        @RequestUser user: RequestUserInfo,
        @RequestBody @Valid request: ReportBoardContentRequest
    ): PlainResponse {
        TODO("""
            게시판 글 신고
        """.trimIndent())
    }
}