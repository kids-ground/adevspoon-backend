package com.adevspoon.api.banner.controller

import com.adevspoon.api.banner.dto.BannerResponse
import io.swagger.v3.oas.annotations.tags.Tag
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RequestParam
import org.springframework.web.bind.annotation.RestController

@RestController
@RequestMapping("/banner")
@Tag(name = "[배너]")
class BannerController {
    @GetMapping
    fun getBannerList(@RequestParam("type") type: String): List<BannerResponse> {
        TODO("""
            - type - Enum으로 받을 필요 있음
            - type, openDate, closeDate에 따라 조회
        """.trimIndent())
    }
}