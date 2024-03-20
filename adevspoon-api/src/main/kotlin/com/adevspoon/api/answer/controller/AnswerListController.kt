package com.adevspoon.api.answer.controller

import com.adevspoon.api.answer.dto.request.AnswerListQueryRequest
import com.adevspoon.api.answer.dto.response.AnswerListResponse
import com.adevspoon.api.common.annotation.RequestUser
import com.adevspoon.api.common.dto.RequestUserInfo
import com.adevspoon.api.config.swagger.SWAGGER_TAG_QUESTION_ANSWER
import io.swagger.v3.oas.annotations.tags.Tag
import jakarta.validation.Valid
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

@RestController
@RequestMapping("/postList")
@Tag(name = SWAGGER_TAG_QUESTION_ANSWER)
class AnswerListController {
    @GetMapping
    fun getAnswerList(
        @RequestUser user: RequestUserInfo,
        @Valid request: AnswerListQueryRequest
    ): AnswerListResponse {
        TODO("""
            - 답변 리스트를 가져온다.
            - sort가 best인경우 현재유저가 받은 Today 질문의 Best 답변을 가져온다.
        """.trimIndent())
    }
}