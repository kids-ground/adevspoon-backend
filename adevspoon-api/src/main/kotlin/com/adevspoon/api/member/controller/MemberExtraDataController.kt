package com.adevspoon.api.member.controller

import com.adevspoon.api.common.annotation.RequestUser
import com.adevspoon.api.common.dto.RequestUserInfo
import com.adevspoon.api.member.dto.request.MemberFavoriteListRequest
import com.adevspoon.api.member.dto.response.MemberFavoriteListResponse
import com.adevspoon.common.dto.PlainResponse
import io.swagger.v3.oas.annotations.tags.Tag
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RequestParam
import org.springframework.web.bind.annotation.RestController

@RestController
@Tag(name = "[유저 - 기타정보]")
class MemberExtraDataController {
    @GetMapping("/nickname")
    fun checkNickname(
        @RequestParam nickname: String = "",
        @RequestUser user: RequestUserInfo,
    ): PlainResponse {
        TODO("""
            - 닉네임 중복체크
            - 무조건 true로 리턴
        """.trimIndent())
    }

    @GetMapping("/myFavorite")
    fun getFavoriteList(
        @RequestUser user: RequestUserInfo,
        request: MemberFavoriteListRequest,
    ): MemberFavoriteListResponse {
        TODO("""
            - 좋아요 누른 것들
            - Query(ModelAttribute에서 ENUM Convert 필요)
            - board, answer, all 모두 가져올 수 있음
        """.trimIndent())
    }
}