package com.adevspoon.api.answer.controller

import com.adevspoon.api.answer.dto.request.AnswerListQueryRequest
import com.adevspoon.api.answer.dto.response.AnswerListResponse
import com.adevspoon.api.answer.service.AnswerService
import com.adevspoon.api.common.annotation.RequestUser
import com.adevspoon.api.common.dto.RequestUserInfo
import com.adevspoon.api.config.swagger.SWAGGER_TAG_QUESTION_ANSWER
import io.swagger.v3.oas.annotations.Operation
import io.swagger.v3.oas.annotations.tags.Tag
import jakarta.validation.Valid
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

@RestController
@RequestMapping("/postList")
@Tag(name = SWAGGER_TAG_QUESTION_ANSWER)
class AnswerListController(
    private val answerService: AnswerService
) {
    @Operation(summary = "특정 질문에 대한 답변 리스트", description = "질문 id에 해당하는 답변 리스트를 가져온다.")
    @GetMapping
    fun getAnswerList(
        @RequestUser user: RequestUserInfo,
        @Valid request: AnswerListQueryRequest
    ): AnswerListResponse {
        return answerService.getAnswerList(request, user.userId)
    }
}