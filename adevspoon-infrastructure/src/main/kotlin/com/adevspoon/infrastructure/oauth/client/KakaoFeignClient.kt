package com.adevspoon.infrastructure.oauth.client

import com.adevspoon.infrastructure.oauth.config.KakaoConfig
import com.adevspoon.infrastructure.oauth.dto.KakaoUserInfoResponse
import org.springframework.cloud.openfeign.FeignClient
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RequestHeader

@FeignClient(
    name = "kakao",
    url = "\${feign.kakao.url}",
    configuration = [KakaoConfig::class]
)
interface KakaoFeignClient {
    @GetMapping("/v2/user/me")
    fun getUserProfile(@RequestHeader("Authorization") accessToken: String?): KakaoUserInfoResponse
}