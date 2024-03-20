package com.adevspoon.api.question.controller

import com.adevspoon.api.common.annotation.RequestUser
import com.adevspoon.api.common.dto.RequestUserInfo
import com.adevspoon.api.config.swagger.SWAGGER_TAG_QUESTION
import com.adevspoon.api.question.dto.request.QuestionCategoryListRequest
import com.adevspoon.api.question.dto.response.QuestionCategoryResponse
import com.adevspoon.api.question.dto.response.QuestionInfoResponse
import io.swagger.v3.oas.annotations.tags.Tag
import jakarta.validation.Valid
import org.springframework.web.bind.annotation.*


@RestController
@RequestMapping("/question")
@Tag(name = SWAGGER_TAG_QUESTION)
class QuestionController {
    @GetMapping
    fun getQuestion(
        @RequestUser user: RequestUserInfo,
        @RequestParam("type", required = false) type: String = "today",
        @RequestParam("questionId", required = false) questionId: Long?,
    ): QuestionInfoResponse {
        TODO("""
            - type = today, date(ex. 2024-03-18)
            - type이 "today"일 경우
                오늘의 질문 리스트를 가져온다.(생성 or get)
            - type이 date일 경우
                type == openDate가 해당 날짜인 질문을 가져온다.(날짜가 오늘이면 생성 or get & 없으면 error)
            - questionId가 있을 경우 question Id로 검색
        """.trimIndent())
    }

    @GetMapping("/question/{questionId}")
    fun getQuestionDetail(
        @RequestUser user: RequestUserInfo,
        @PathVariable questionId: Long,
    ): QuestionInfoResponse {
        TODO("""
            - user가 발급받은 question을 Id기반으로 가져오기
        """.trimIndent())
    }

    @GetMapping("/question/category")
    fun getQuestionCategoryList(
        @RequestUser user: RequestUserInfo,
        @Valid request: QuestionCategoryListRequest
    ): List<QuestionCategoryResponse> {
        TODO("""
            - limit 기본값 30개, offset 기본값 0개
            - Category 가져오기 - 내가 선택한 카테고리인지, 더 이상 받을 질문이 없는지도 체크
        """.trimIndent())
    }
}