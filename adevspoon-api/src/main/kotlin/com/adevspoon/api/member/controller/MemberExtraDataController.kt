package com.adevspoon.api.member.controller

import com.adevspoon.api.common.annotation.RequestUser
import com.adevspoon.api.common.dto.RequestUserInfo
import com.adevspoon.api.config.swagger.SWAGGER_TAG_USER_ETC
import com.adevspoon.api.member.dto.request.MemberFavoriteListRequest
import com.adevspoon.api.member.dto.response.MemberFavoriteListResponse
import io.swagger.v3.oas.annotations.Operation
import io.swagger.v3.oas.annotations.tags.Tag
import jakarta.validation.Valid
import jakarta.validation.constraints.Size
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RequestParam
import org.springframework.web.bind.annotation.RestController

@RestController
@Tag(name = SWAGGER_TAG_USER_ETC)
class MemberExtraDataController {

    @Operation(summary = "닉네임 중복체크", description = "현재 정책이 바뀌어 무조건 true로 리턴")
    @GetMapping("/nickname")
    fun checkNickname(
        @RequestUser user: RequestUserInfo,
        @RequestParam @Size(min = 2, max = 10, message = "2자 이상 10자 이하여야 합니다") nickname: String,
    ): String {
        return "가능한 닉네임입니다"
    }

    @Operation(summary = "좋아요 리스트", description = "좋아요 누른 포스트 가져오기(게시글, 질문답변 가능)")
    @GetMapping("/myFavorite")
    fun getFavoriteList(
        @RequestUser user: RequestUserInfo,
        @Valid request: MemberFavoriteListRequest,
    ): MemberFavoriteListResponse {
        TODO("""
            - 좋아요 누른 것들
            - Query(ModelAttribute에서 ENUM Convert 필요)
            - board, answer, all 모두 가져올 수 있음
        """.trimIndent())
    }
}