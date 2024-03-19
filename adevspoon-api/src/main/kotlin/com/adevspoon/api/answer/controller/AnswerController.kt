package com.adevspoon.api.answer.controller

import com.adevspoon.api.answer.dto.*
import com.adevspoon.api.answer.dto.request.*
import com.adevspoon.api.answer.dto.response.AnswerInfoResponse
import com.adevspoon.api.common.annotation.RequestUser
import com.adevspoon.api.common.dto.RequestUserInfo
import com.adevspoon.common.dto.PlainResponse
import io.swagger.v3.oas.annotations.tags.Tag
import org.springframework.web.bind.annotation.*

@RestController
@RequestMapping("/post")
@Tag(name = "[질문 - 답변]")
class AnswerController {
    @PostMapping
    fun registerAnswer(
        @RequestBody request: RegisterAnswerRequest,
        @RequestUser user: RequestUserInfo,
    ): AnswerInfoResponse {
        TODO("""
            - 답변을 등록한다.
        """.trimIndent())
    }

    @GetMapping("/{answerId}")
    fun getAnswerDetail(
        @PathVariable answerId: Long,
        @RequestParam("type") postType: PostType = PostType.ANSWER,
        @RequestUser user: RequestUserInfo,
    ): AnswerInfoResponse {
        TODO("""
            - 특정 답변을 가져온다.
        """.trimIndent())
    }

    @PutMapping("/{answerId}")
    fun modifyAnswer(
        @PathVariable answerId: Long,
        @RequestBody request: AnswerUpdateRequest,
        @RequestUser user: RequestUserInfo,
    ): AnswerInfoResponse {
        TODO("""
            - 답변을 수정한다.
        """.trimIndent())
    }

    @PostMapping("/{answerId}/report")
    fun reportAnswer(
        @PathVariable answerId: Long,
        @RequestBody request: AnswerReportRequest,
        @RequestUser user: RequestUserInfo,
    ): PlainResponse {
        TODO("""
            - 답변을 신고한다.
            - 내 답변인 경우 신고할 수 없다.
            - 이미 내가 신고한 것인지도 확인해야 한다.
        """.trimIndent())
    }

    @PostMapping("/{id}/favorite")
    fun likeAnswer(
        @PathVariable id: Long,
        @RequestBody request: LikeRequest,
        @RequestUser user: RequestUserInfo,
    ): PlainResponse {
        TODO("""
            - 답변/게시글을 좋아요한다.
        """.trimIndent())
    }
}