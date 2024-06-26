package com.adevspoon.api.member.controller

import com.adevspoon.api.auth.service.AuthService
import com.adevspoon.api.common.annotation.RequestUser
import com.adevspoon.api.common.annotation.SecurityIgnored
import com.adevspoon.api.common.dto.RequestUserInfo
import com.adevspoon.api.config.swagger.SWAGGER_TAG_USER
import com.adevspoon.api.member.dto.request.MemberActivityRequest
import com.adevspoon.api.member.dto.request.MemberProfileUpdateRequest
import com.adevspoon.api.member.dto.request.SocialLoginRequest
import com.adevspoon.api.member.dto.response.AchievedBadgeResponse
import com.adevspoon.api.member.dto.response.MemberActivityResponse
import com.adevspoon.api.member.dto.response.MemberAndTokenResponse
import com.adevspoon.api.member.dto.response.MemberProfileResponse
import com.adevspoon.api.member.service.MemberService
import io.swagger.v3.oas.annotations.Operation
import io.swagger.v3.oas.annotations.tags.Tag
import jakarta.validation.Valid
import org.springframework.web.bind.annotation.*

@RestController
@RequestMapping("/member")
@Tag(name = SWAGGER_TAG_USER)
class MemberController(
    private val authService: AuthService,
    private val memberService: MemberService
) {
    @Operation(summary = "로그인/회원가입", description = "kakao, apple을 통한 로그인/회원가입")
    @SecurityIgnored
    @PostMapping
    fun socialLogin(@RequestBody @Valid request: SocialLoginRequest) : MemberAndTokenResponse {
        return authService.signIn(request)
    }

    @Operation(summary = "프로필 수정", description = "프로필 정보 수정용")
    @PatchMapping
    fun updateProfile(
        @RequestUser requestUser: RequestUserInfo,
        @Valid request: MemberProfileUpdateRequest
    ) : MemberProfileResponse {
        return memberService.updateProfile(requestUser.userId, request)
    }

    @Operation(summary = "멤버 정보 가져오기", description = "memberId에 해당하는 멤버 정보 가져오기")
    @GetMapping("/{memberId}")
    fun getProfile(
        @RequestUser requestUser: RequestUserInfo,
        @PathVariable memberId: Long,
    ) : MemberProfileResponse {
        return memberService.getProfile(memberId)
    }

    @Operation(summary = "회원탈퇴", description = "유저 활동 기록도 지워짐 주의!")
    @DeleteMapping
    fun withdrawal(@RequestUser requestUser: RequestUserInfo): String {
        return authService.withdraw(requestUser.userId)
    }

    @Operation(summary = "출석", description = "앱 내 출석체크")
    @GetMapping("/attendance")
    fun attendance(@RequestUser requestUser: RequestUserInfo): MemberProfileResponse {
        return memberService.attend(requestUser.userId)
    }

    @Operation(summary = "뱃지 리스트 가져오기", description = "획득 여부, 달성률이 포함되어 있음")
    @GetMapping("/badge")
    fun getAchievedBadge(@RequestUser requestUser: RequestUserInfo): List<AchievedBadgeResponse> {
        return memberService.getBadgeList(requestUser.userId)
    }

    @Operation(summary = "답변 활동기록 가져오기", description = "year, month를 통해 일별 답변활동 수 가져오기")
    @GetMapping("/answerGrass")
    fun getActivityInfo(
        @RequestUser requestUser: RequestUserInfo,
        @Valid request: MemberActivityRequest
    ): List<MemberActivityResponse> {
        return memberService.getDailyAnswersCountForMonth(request, requestUser.userId)
    }
}