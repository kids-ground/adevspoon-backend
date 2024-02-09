package com.adevspoon.infra.oauth.internal

import com.adevspoon.infra.oauth.dto.AppleAuthKeysResponse
import org.springframework.cloud.openfeign.FeignClient
import org.springframework.web.bind.annotation.GetMapping


@FeignClient(name = "apple", url = "\${feign.apple.url}")
interface AppleFeignClient {
    @GetMapping("/auth/keys")
    fun authKeys(): AppleAuthKeysResponse
}