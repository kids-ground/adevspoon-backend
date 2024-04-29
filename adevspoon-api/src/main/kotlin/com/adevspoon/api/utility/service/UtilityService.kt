package com.adevspoon.api.utility.service

import com.adevspoon.api.common.annotation.ApplicationService
import com.adevspoon.api.utility.dto.response.BannerResponse
import com.adevspoon.api.utility.dto.response.PopupResponse
import com.adevspoon.api.utility.dto.response.WallPaperResponse
import com.adevspoon.domain.utility.service.UtilityDomainService

@ApplicationService
class UtilityService(
    private val utilityDomainService: UtilityDomainService
) {
    fun getWallPaper(): WallPaperResponse {
        return WallPaperResponse.from(
            utilityDomainService.getWallPaper()
        )
    }

    fun getPopup(): PopupResponse {
        return PopupResponse.from(
            utilityDomainService.getPopup()
        )
    }

    fun getBannerList(): List<BannerResponse> {
        return utilityDomainService.getBannerList()
            .map { BannerResponse.from(it) }
    }
}