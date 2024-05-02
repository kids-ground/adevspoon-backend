package com.adevspoon.api.question.controller

import com.adevspoon.api.common.annotation.RequestUser
import com.adevspoon.api.common.dto.RequestUserInfo
import com.adevspoon.api.config.swagger.SWAGGER_TAG_QUESTION
import com.adevspoon.api.question.dto.request.QuestionListRequest
import com.adevspoon.api.question.dto.response.QuestionListResponse
import com.adevspoon.api.question.service.QuestionService
import io.swagger.v3.oas.annotations.Operation
import io.swagger.v3.oas.annotations.tags.Tag
import jakarta.validation.Valid
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController


@RestController
@RequestMapping("/questionList")
@Tag(name = SWAGGER_TAG_QUESTION)
class QuestionListController(
    private val questionService: QuestionService
) {
    @Operation(summary = "발급받은 질문 리스트 조회")
    @GetMapping
    fun getQuestionList(
        @RequestUser user: RequestUserInfo,
        @Valid request: QuestionListRequest
    ): QuestionListResponse {
        return questionService.getQuestionList(user.userId, request)
    }
}