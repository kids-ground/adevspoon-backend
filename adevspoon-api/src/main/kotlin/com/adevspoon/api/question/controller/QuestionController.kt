package com.adevspoon.api.question.controller

import com.adevspoon.api.common.annotation.RequestUser
import com.adevspoon.api.common.dto.RequestUserInfo
import com.adevspoon.api.config.swagger.SWAGGER_TAG_QUESTION
import com.adevspoon.api.question.dto.request.QuestionCategoryListRequest
import com.adevspoon.api.question.dto.request.QuestionRequest
import com.adevspoon.api.question.dto.response.QuestionCategoryResponse
import com.adevspoon.api.question.dto.response.QuestionInfoResponse
import com.adevspoon.api.question.service.QuestionService
import io.swagger.v3.oas.annotations.Operation
import io.swagger.v3.oas.annotations.tags.Tag
import jakarta.validation.Valid
import org.springframework.web.bind.annotation.*


@RestController
@RequestMapping("/question")
@Tag(name = SWAGGER_TAG_QUESTION)
class QuestionController(
    private val questionService: QuestionService,
) {
    @Operation(summary = "질문 가져오기", description = "questionId가 있다면 해당 질문을 가져오고 없다면 오늘의 질문을 가져옵니다.")
    @GetMapping
    fun getQuestion(
        @RequestUser user: RequestUserInfo,
        @Valid request: QuestionRequest,
    ): QuestionInfoResponse {
        return if (request.questionId != null)
            questionService.getQuestionById(user.userId, request.questionId)
        else questionService.getTodayQuestion(user.userId)
    }

    @Operation(summary = "질문 id 기반으로 상세정보 가져오기")
    @GetMapping("/{questionId}")
    fun getQuestionDetail(
        @RequestUser user: RequestUserInfo,
        @PathVariable questionId: Long,
    ): QuestionInfoResponse {
        return questionService.getQuestionById(user.userId, questionId)
    }

    @Operation(summary = "질문 카테고리 리스트 가져오기")
    @GetMapping("/category")
    fun getQuestionCategoryList(
        @RequestUser user: RequestUserInfo,
        @Valid request: QuestionCategoryListRequest
    ): List<QuestionCategoryResponse> {
        return questionService.getQuestionCategories(user.userId)
    }
}