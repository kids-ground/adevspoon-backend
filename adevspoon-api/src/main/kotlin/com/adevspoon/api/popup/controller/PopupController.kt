package com.adevspoon.api.popup.controller

import com.adevspoon.api.popup.dto.PopupResponse
import io.swagger.v3.oas.annotations.tags.Tag
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

@RestController
@RequestMapping("/popup")
@Tag(name = "[팝업]")
class PopupController {
    @GetMapping
    fun getPopup(): PopupResponse {
        TODO("""
            - 현재 팝업가져오기
            - openDate, closeDate를 현재 Date랑 비교해서 현재 사용중인거 가져오기
        """.trimIndent())
    }
}