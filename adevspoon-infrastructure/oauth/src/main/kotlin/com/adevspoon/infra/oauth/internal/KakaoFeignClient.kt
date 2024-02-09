package com.adevspoon.infra.oauth.internal

import com.adevspoon.infra.oauth.dto.KakaoUserInfoResponse
import org.springframework.cloud.openfeign.FeignClient
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RequestHeader


@FeignClient(name = "kakao", url = "\${feign.kakao.url}")
interface KakaoFeignClient {
    @GetMapping("/v2/user/me")
    fun getUserProfile(@RequestHeader("Authorization") accessToken: String?): KakaoUserInfoResponse
}