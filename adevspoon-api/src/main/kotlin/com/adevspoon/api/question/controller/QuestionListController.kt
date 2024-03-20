package com.adevspoon.api.question.controller

import com.adevspoon.api.common.annotation.RequestUser
import com.adevspoon.api.common.dto.RequestUserInfo
import com.adevspoon.api.config.swagger.SWAGGER_TAG_QUESTION
import com.adevspoon.api.question.dto.request.QuestionListRequest
import com.adevspoon.api.question.dto.response.QuestionListResponse
import io.swagger.v3.oas.annotations.tags.Tag
import jakarta.validation.Valid
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController


@RestController
@RequestMapping("/questionList")
@Tag(name = SWAGGER_TAG_QUESTION)
class QuestionListController {
    @GetMapping
    fun getQuestionList(
        @RequestUser user: RequestUserInfo,
        @Valid request: QuestionListRequest
    ): QuestionListResponse {
        TODO("""
            - 내가 발급받은 질문 리스트 조회
            - 쿼리 Pageable한걸로 한 번더 매핑하기 - limit, offset, sort(newest, oldest), isAnswered
            - isAnswered가 true면 answerId is not null인걸로
            - 응답 - 질문 리스트, 다음 페이지 uri 
        """.trimIndent())
    }
}