package com.adevspoon.api.wallPaper.controller

import com.adevspoon.api.wallPaper.dto.WallPaperResponse
import io.swagger.v3.oas.annotations.tags.Tag
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

@RestController
@RequestMapping("/wallPaper")
@Tag(name = "[명언]")
class WallPaperController {

    @GetMapping
    fun getWallPaper(): WallPaperResponse {
        TODO("""
            - type - 명언 좀 하나 랜덤하게 가져오기
        """.trimIndent())
    }
}