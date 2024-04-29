package com.adevspoon.api.answer.controller

import com.adevspoon.api.answer.dto.*
import com.adevspoon.api.answer.dto.request.*
import com.adevspoon.api.answer.dto.response.AnswerInfoResponse
import com.adevspoon.api.answer.service.AnswerService
import com.adevspoon.api.common.annotation.RequestUser
import com.adevspoon.api.common.dto.RequestUserInfo
import com.adevspoon.api.config.swagger.SWAGGER_TAG_QUESTION_ANSWER
import io.swagger.v3.oas.annotations.Operation
import io.swagger.v3.oas.annotations.tags.Tag
import jakarta.validation.Valid
import org.springframework.web.bind.annotation.*

@RestController
@RequestMapping("/post")
@Tag(name = SWAGGER_TAG_QUESTION_ANSWER)
class AnswerController(
    private val answerService: AnswerService
) {
    @Operation(summary = "답변 등록", description = "답변을 등록하고 답변 정보를 반환한다.")
    @PostMapping
    fun registerAnswer(
        @RequestUser requestUser: RequestUserInfo,
        @RequestBody @Valid request: RegisterAnswerRequest,
    ): AnswerInfoResponse {
        return answerService.registerAnswer(request, requestUser.userId)
    }

    @Operation(summary = "답변 정보", description = "id를 기반으로 답변 정보를 반환한다.")
    @GetMapping("/{answerId}")
    fun getAnswerDetail(
        @RequestUser user: RequestUserInfo,
        @PathVariable answerId: Long,
        @RequestParam("type") postType: AnswerType = AnswerType.ANSWER,
    ): AnswerInfoResponse {
        return answerService.getAnswerDetail(answerId, user.userId)
    }

    @Operation(summary = "답변 수정", description = "답변을 수정하고 수정된 답변 정보를 반환한다.")
    @PutMapping("/{answerId}")
    fun modifyAnswer(
        @RequestUser user: RequestUserInfo,
        @PathVariable answerId: Long,
        @RequestBody @Valid request: AnswerUpdateRequest,
    ): AnswerInfoResponse {
        return answerService.modifyAnswer(answerId, request, user.userId)
    }

    @Operation(summary = "답변 좋아요", description = "답변을 좋아요 or 좋아요 취소한다.")
    @PostMapping("/{answerId}/favorite")
    fun likeAnswer(
        @RequestUser user: RequestUserInfo,
        @PathVariable answerId: Long,
        @RequestBody @Valid request: LikeRequest,
    ): String {
        return answerService.likeAnswer(answerId, user.userId, request.like)
    }

    @Operation(summary = "답변 신고", description = "올바르지 않은 답변에 대해 신고를 한다.")
    @PostMapping("/{answerId}/report")
    fun reportAnswer(
        @RequestUser user: RequestUserInfo,
        @PathVariable answerId: Long,
        @RequestBody @Valid request: AnswerReportRequest,
    ): String {
        return answerService.reportAnswer(answerId, user.userId)
    }
}