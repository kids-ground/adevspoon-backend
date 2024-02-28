package com.adevspoon.api.member.controller

import com.adevspoon.api.auth.service.AuthService
import com.adevspoon.api.member.dto.request.SocialLoginRequest
import com.adevspoon.api.member.dto.response.SocialLoginResponse
import io.swagger.v3.oas.annotations.Operation
import io.swagger.v3.oas.annotations.tags.Tag
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

@RestController
@RequestMapping("member")
@Tag(name = "[유저]")
class MemberController(
    private val authService: AuthService
) {
    @Operation(summary = "로그인/회원가입", description = "kakao, apple을 통한 로그인/회원가입")
    @PostMapping
    fun socialLogin(@RequestBody request: SocialLoginRequest) : ResponseEntity<SocialLoginResponse> {
        return ResponseEntity.ok().body(authService.signIn(request))
    }
}