package com.adevspoon.api.utility.controller

import com.adevspoon.api.config.swagger.SWAGGER_TAG_ETC
import com.adevspoon.api.utility.dto.response.BannerResponse
import com.adevspoon.api.utility.dto.response.PopupResponse
import com.adevspoon.api.utility.dto.response.WallPaperResponse
import io.swagger.v3.oas.annotations.Operation
import io.swagger.v3.oas.annotations.tags.Tag
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RequestParam
import org.springframework.web.bind.annotation.RestController


@RestController
@Tag(name = SWAGGER_TAG_ETC)
class UtilityController {

    @Operation(summary = "명언 가져오기", description = "랜덤으로 하나의 명언을 가져옵니다")
    @GetMapping("/wallPaper")
    fun getWallPaper(): WallPaperResponse {
        TODO("""
            - type - 명언 좀 하나 랜덤하게 가져오기
        """.trimIndent())
    }

    @Operation(summary = "팝업 가져오기", description = "현재 게시중인 팝업을 가져옵니다")
    @GetMapping("/popup")
    fun getPopup(): PopupResponse {
        TODO("""
            - 현재 팝업가져오기
            - openDate, closeDate를 현재 Date랑 비교해서 현재 사용중인거 가져오기
        """.trimIndent())
    }

    @Operation(summary = "배너 가져오기", description = "type(위치)에 맞는 현재 게시중인 배너를 가져옵니다")
    @GetMapping("/banner")
    fun getBannerList(@RequestParam("type") type: String): List<BannerResponse> {
        TODO("""
            - type - Enum으로 받을 필요 있음
            - type, openDate, closeDate에 따라 조회
        """.trimIndent())
    }
}