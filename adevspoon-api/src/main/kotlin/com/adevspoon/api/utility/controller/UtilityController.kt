package com.adevspoon.api.utility.controller

import com.adevspoon.api.config.swagger.SWAGGER_TAG_ETC
import com.adevspoon.api.utility.dto.response.BannerResponse
import com.adevspoon.api.utility.dto.response.PopupResponse
import com.adevspoon.api.utility.dto.response.WallPaperResponse
import com.adevspoon.api.utility.service.UtilityService
import io.swagger.v3.oas.annotations.Operation
import io.swagger.v3.oas.annotations.tags.Tag
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RequestParam
import org.springframework.web.bind.annotation.RestController


@RestController
@Tag(name = SWAGGER_TAG_ETC)
class UtilityController(
    private val utilityService: UtilityService
) {
    @Operation(summary = "명언 가져오기", description = "랜덤으로 하나의 명언을 가져옵니다")
    @GetMapping("/wallPaper")
    fun getWallPaper(): WallPaperResponse {
        return utilityService.getWallPaper()
    }

    @Operation(summary = "팝업 가져오기", description = "현재 게시중인 팝업을 가져옵니다")
    @GetMapping("/popup")
    fun getPopup(): PopupResponse {
        return utilityService.getPopup()
    }

    @Operation(summary = "배너 가져오기", description = "현재는 무조건 Home 배너만 가져옵니다")
    @GetMapping("/banner")
    fun getBannerList(@RequestParam("type") type: String): List<BannerResponse> {
        return utilityService.getBannerList()
    }
}