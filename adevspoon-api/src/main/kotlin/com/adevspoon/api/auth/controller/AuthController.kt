package com.adevspoon.api.auth.controller

import com.adevspoon.api.auth.dto.request.RefreshTokenRequest
import com.adevspoon.api.auth.dto.response.TokenResponse
import com.adevspoon.api.common.annotation.RequestUser
import com.adevspoon.api.common.annotation.SecurityIgnored
import com.adevspoon.api.common.dto.RequestUserInfo
import com.adevspoon.common.dto.PlainResponse
import io.swagger.v3.oas.annotations.tags.Tag
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RestController

@RestController
@Tag(name = "[계정]")
class AuthController {
    @PostMapping("/refresh")
    @SecurityIgnored
    fun refreshToken(
        @RequestBody request: RefreshTokenRequest,
    ): TokenResponse {
        TODO("""
            - 토큰 재발급
            - refreshToken 만료시간 체크 및 user에 업데이트
        """.trimIndent())
    }

    @PostMapping("/logout")
    fun logout(
        @RequestUser user: RequestUserInfo
    ): PlainResponse {
        TODO("""
            - 로그아웃
        """.trimIndent())
    }

    @GetMapping("/login")
    @SecurityIgnored
    fun login() {
        TODO("""
            - 로그인 (웹, security에서 처리하기) 
        """.trimIndent())
    }

    @GetMapping("/member/login/callback")
    @SecurityIgnored
    fun loginCallback() {
        TODO("""
            - loginCallback (웹, security에서 처리하기)
        """.trimIndent())
    }
}