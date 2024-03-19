package com.adevspoon.api.member.controller

import com.adevspoon.api.auth.service.AuthService
import com.adevspoon.api.common.annotation.RequestUser
import com.adevspoon.api.common.dto.RequestUserInfo
import com.adevspoon.api.member.dto.request.MemberActivityRequest
import com.adevspoon.api.member.dto.request.SocialLoginRequest
import com.adevspoon.api.member.dto.request.MemberProfileUpdateRequest
import com.adevspoon.api.member.dto.response.AchievedBadgeResponse
import com.adevspoon.api.member.dto.response.MemberActivityResponse
import com.adevspoon.api.member.dto.response.MemberAndTokenResponse
import com.adevspoon.api.member.dto.response.MemberProfileResponse
import com.adevspoon.api.member.service.MemberService
import com.adevspoon.common.dto.PlainResponse
import io.swagger.v3.oas.annotations.Operation
import io.swagger.v3.oas.annotations.tags.Tag
import org.springframework.web.bind.annotation.*

@RestController
@RequestMapping("/member")
@Tag(name = "[유저]")
class MemberController(
    private val authService: AuthService,
    private val memberService: MemberService
) {
    @Operation(summary = "로그인/회원가입", description = "kakao, apple을 통한 로그인/회원가입")
    @PostMapping
    fun socialLogin(@RequestBody request: SocialLoginRequest) : MemberAndTokenResponse {
        return authService.signIn(request)
    }

    @Operation(summary = "프로필 수정", description = "프로필 정보 수정용")
    @PatchMapping
    fun updateProfile(
        @RequestUser requestUser: RequestUserInfo,
        request: MemberProfileUpdateRequest
    ) : MemberProfileResponse {
        return memberService.updateProfile(requestUser.userId, request)
    }

    @GetMapping("/{memberId}")
    fun getProfile(
        @PathVariable memberId: Long,
        @RequestUser requestUser: RequestUserInfo,
    ) : MemberProfileResponse {
        TODO("""
            - 특정 유저 정보 가져오기
        """.trimIndent())
    }

    @DeleteMapping
    fun withdrawal(@RequestUser requestUser: RequestUserInfo): PlainResponse {
        TODO("""
            - 회원탈퇴
            - 어디까지 삭제해야하나?
        """.trimIndent())
    }

    @GetMapping("/attendance")
    fun attendance(@RequestUser requestUser: RequestUserInfo): MemberProfileResponse {
        TODO("""
            - 출석 체크
        """.trimIndent())
    }

    @GetMapping("/badge")
    fun getAchievedBadge(@RequestUser requestUser: RequestUserInfo): AchievedBadgeResponse {
        TODO("""
            - 내가 얻은 뱃지 정보 가져오기
        """.trimIndent())
    }

    @GetMapping("/answerGrass")
    fun getActivityInfo(
        @RequestUser requestUser: RequestUserInfo,
        request: MemberActivityRequest
    ): List<MemberActivityResponse> {
        TODO("""
            - year, month에 맞는 활동 정보 가져오기
        """.trimIndent())
    }
}