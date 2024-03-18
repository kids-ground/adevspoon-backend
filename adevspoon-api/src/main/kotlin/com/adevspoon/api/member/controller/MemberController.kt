package com.adevspoon.api.member.controller

import com.adevspoon.api.auth.service.AuthService
import com.adevspoon.api.common.annotation.RequestUser
import com.adevspoon.api.common.dto.RequestUserInfo
import com.adevspoon.api.member.dto.request.SocialLoginRequest
import com.adevspoon.api.member.dto.request.MemberProfileUpdateRequest
import com.adevspoon.api.member.dto.response.MemberAndTokenResponse
import com.adevspoon.api.member.dto.response.MemberProfileResponse
import com.adevspoon.api.member.service.MemberService
import io.swagger.v3.oas.annotations.Operation
import io.swagger.v3.oas.annotations.tags.Tag
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.PatchMapping
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

@RestController
@RequestMapping("/member")
@Tag(name = "[유저]")
class MemberController(
    private val authService: AuthService,
    private val memberService: MemberService
) {
    @Operation(summary = "로그인/회원가입", description = "kakao, apple을 통한 로그인/회원가입")
    @PostMapping
    fun socialLogin(@RequestBody request: SocialLoginRequest) : ResponseEntity<MemberAndTokenResponse> {
        return ResponseEntity.ok().body(authService.signIn(request))
    }

    @Operation(summary = "프로필 수정", description = "프로필 수정")
    @PatchMapping
    fun updateProfile(@RequestUser requestUser: RequestUserInfo, request: MemberProfileUpdateRequest) : ResponseEntity<MemberProfileResponse> {
        return ResponseEntity.ok().body(memberService.updateProfile(requestUser.userId, request))
    }
}