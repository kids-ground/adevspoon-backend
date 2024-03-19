package com.adevspoon.api.board.controller

import com.adevspoon.api.board.dto.request.*
import com.adevspoon.api.board.dto.response.BoardInfoResponse
import com.adevspoon.api.board.dto.response.BoardListResponse
import com.adevspoon.api.board.dto.response.BoardTagResponse
import com.adevspoon.api.common.annotation.RequestUser
import com.adevspoon.api.common.dto.RequestUserInfo
import com.adevspoon.common.dto.PlainResponse
import io.swagger.v3.oas.annotations.tags.Tag
import org.springframework.web.bind.annotation.*

@RestController
@RequestMapping("/board")
@Tag(name = "[게시판]")
class BoardController {
    @GetMapping("/tag")
    fun getBoardTagList(): BoardTagResponse {
        TODO("""
            게시판 전체 태그 리스트 조회
        """.trimIndent())
    }

    @PostMapping("/post")
    fun registerBoardPost(
        @RequestBody request: RegisterBoardPostRequest,
        @RequestUser user: RequestUserInfo,
    ): BoardInfoResponse {
        TODO("""
            게시판 글 등록
        """.trimIndent())
    }

    @GetMapping("/post/{postId}")
    fun getBoardPost(
        @PathVariable postId: Long,
        @RequestUser user: RequestUserInfo,
    ): BoardInfoResponse {
        TODO("""
            게시판 글 조회
        """.trimIndent())
    }

    @GetMapping("/post")
    fun getBoardPostList(
        @RequestUser user: RequestUserInfo,
        request: BoardListRequest,
    ): BoardListResponse {
        TODO("""
            게시판 글 리스트 조회
        """.trimIndent())
    }

    @PatchMapping("/post")
    fun updateBoardPost(
        @RequestUser user: RequestUserInfo,
        @RequestBody request: UpdateBoardPostRequest,
    ): BoardInfoResponse {
        TODO("""
            게시판 글 수정
        """.trimIndent())
    }

    @DeleteMapping("/post")
    fun deleteBoardPost(
        @RequestUser user: RequestUserInfo,
        @RequestBody request: BoardDeleteRequest,
    ): PlainResponse {
        TODO("""
            게시판 글 삭제
        """.trimIndent())
    }

    @PostMapping("/like")
    fun likeBoardPost(
        @RequestUser user: RequestUserInfo,
        @RequestBody request: LikeBoardContentRequest
    ): PlainResponse {
        TODO("""
            게시판 글 좋아요
            - post, comment 둘 다 있음
        """.trimIndent())
    }

    @PostMapping("/report")
    fun reportBoardPost(
        @RequestUser user: RequestUserInfo,
        @RequestBody request: ReportBoardContentRequest
    ): PlainResponse {
        TODO("""
            게시판 글 신고
        """.trimIndent())
    }
}