package com.adevspoon.infrastructure.oauth.client

import com.adevspoon.infrastructure.oauth.dto.AppleAuthKeysResponse
import org.springframework.cloud.openfeign.FeignClient
import org.springframework.web.bind.annotation.GetMapping


@FeignClient(name = "apple", url = "\${feign.apple.url}")
interface AppleFeignClient {
    @GetMapping("/auth/keys")
    fun getAuthKeys(): AppleAuthKeysResponse
}