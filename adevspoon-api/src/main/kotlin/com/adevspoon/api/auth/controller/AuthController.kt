package com.adevspoon.api.auth.controller

import com.adevspoon.api.auth.dto.request.RefreshTokenRequest
import com.adevspoon.api.auth.dto.response.TokenResponse
import com.adevspoon.api.common.annotation.RequestUser
import com.adevspoon.api.common.annotation.SecurityIgnored
import com.adevspoon.api.common.dto.RequestUserInfo
import com.adevspoon.api.config.swagger.SWAGGER_TAG_ACCOUNT
import io.swagger.v3.oas.annotations.Operation
import io.swagger.v3.oas.annotations.tags.Tag
import jakarta.validation.Valid
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RestController

@RestController
@Tag(name = SWAGGER_TAG_ACCOUNT)
class AuthController {

    @Operation(summary = "토큰 재발급", description = "access/refresh 토큰 재발급")
    @PostMapping("/refresh")
    @SecurityIgnored
    fun refreshToken(
        @RequestBody @Valid request: RefreshTokenRequest,
    ): TokenResponse {
        TODO("""
            - 토큰 재발급
            - refreshToken 만료시간 체크 및 user에 업데이트
        """.trimIndent())
    }

    @Operation(summary = "로그아웃", description = "로그인 상태에서 로그아웃 처리")
    @PostMapping("/logout")
    fun logout(
        @RequestUser user: RequestUserInfo
    ): String {
        TODO("""
            - 로그아웃
        """.trimIndent())
    }

    @Operation(summary = "(웹) 로그인", description = "웹 전용 카카오 로그인")
    @GetMapping("/login")
    @SecurityIgnored
    fun login() {
        TODO("""
            - 로그인 (웹, security에서 처리하기) 
        """.trimIndent())
    }

    @Operation(summary = "(웹) 카카오로그인 콜백 API", description = "웹 전용 카카오 로그인 콜백 API")
    @GetMapping("/member/login/callback")
    @SecurityIgnored
    fun loginCallback() {
        TODO("""
            - loginCallback (웹, security에서 처리하기)
        """.trimIndent())
    }
}