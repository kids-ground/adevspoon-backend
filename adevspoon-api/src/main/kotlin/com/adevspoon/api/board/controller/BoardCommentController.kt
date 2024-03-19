package com.adevspoon.api.board.controller

import com.adevspoon.api.board.dto.request.RegisterBoardCommentRequest
import com.adevspoon.api.board.dto.response.BoardCommentListResponse
import com.adevspoon.api.board.dto.response.BoardCommentResponse
import com.adevspoon.api.common.annotation.RequestUser
import com.adevspoon.api.common.dto.RequestUserInfo
import com.adevspoon.common.dto.PlainResponse
import io.swagger.v3.oas.annotations.tags.Tag
import org.springframework.web.bind.annotation.DeleteMapping
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RequestParam
import org.springframework.web.bind.annotation.RestController

@RestController
@RequestMapping("/board/comment")
@Tag(name = "[게시판 - 댓글]")
class BoardCommentController {
    @GetMapping
    fun getBoardCommentList(
        @RequestUser user: RequestUserInfo,
        @RequestParam postId: Long,
    ): BoardCommentListResponse {
        TODO("""
            게시판 댓글 리스트 조회
        """.trimIndent())
    }

    @PostMapping
    fun registerBoardComment(
        @RequestUser user: RequestUserInfo,
        @RequestBody request: RegisterBoardCommentRequest
    ): BoardCommentResponse {
        TODO("""
            게시판 댓글 등록
        """.trimIndent())
    }

    @DeleteMapping
    fun deleteBoardComment(
        @RequestUser user: RequestUserInfo,
        @RequestParam commentId: Long
    ): PlainResponse {
        TODO("""
            게시판 댓글 삭제
        """.trimIndent())
    }
}