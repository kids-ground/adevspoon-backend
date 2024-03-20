package com.adevspoon.api.answer.controller

import com.adevspoon.api.answer.dto.*
import com.adevspoon.api.answer.dto.request.*
import com.adevspoon.api.answer.dto.response.AnswerInfoResponse
import com.adevspoon.api.common.annotation.RequestUser
import com.adevspoon.api.common.dto.RequestUserInfo
import com.adevspoon.api.config.swagger.SWAGGER_TAG_QUESTION_ANSWER
import com.adevspoon.common.dto.PlainResponse
import io.swagger.v3.oas.annotations.tags.Tag
import jakarta.validation.Valid
import org.springframework.web.bind.annotation.*

@RestController
@RequestMapping("/post")
@Tag(name = SWAGGER_TAG_QUESTION_ANSWER)
class AnswerController {
    @PostMapping
    fun registerAnswer(
        @RequestUser user: RequestUserInfo,
        @RequestBody @Valid request: RegisterAnswerRequest,
    ): AnswerInfoResponse {
        TODO("""
            - 답변을 등록한다.
        """.trimIndent())
    }

    @GetMapping("/{answerId}")
    fun getAnswerDetail(
        @RequestUser user: RequestUserInfo,
        @PathVariable answerId: Long,
        @RequestParam("type") postType: AnswerType = AnswerType.ANSWER, // TODO: Converter 및 Validation 필요
    ): AnswerInfoResponse {
        TODO("""
            - 특정 답변을 가져온다.
        """.trimIndent())
    }

    @PutMapping("/{answerId}")
    fun modifyAnswer(
        @RequestUser user: RequestUserInfo,
        @PathVariable answerId: Long,
        @RequestBody @Valid request: AnswerUpdateRequest,
    ): AnswerInfoResponse {
        TODO("""
            - 답변을 수정한다.
        """.trimIndent())
    }

    @PostMapping("/{answerId}/report")
    fun reportAnswer(
        @RequestUser user: RequestUserInfo,
        @PathVariable answerId: Long,
        @RequestBody @Valid request: AnswerReportRequest,
    ): PlainResponse {
        TODO("""
            - 답변을 신고한다.
            - 내 답변인 경우 신고할 수 없다.
            - 이미 내가 신고한 것인지도 확인해야 한다.
        """.trimIndent())
    }

    @PostMapping("/{id}/favorite")
    fun likeAnswer(
        @RequestUser user: RequestUserInfo,
        @PathVariable id: Long,
        @RequestBody @Valid request: LikeRequest,
    ): PlainResponse {
        TODO("""
            - 답변/게시글을 좋아요한다.
        """.trimIndent())
    }
}