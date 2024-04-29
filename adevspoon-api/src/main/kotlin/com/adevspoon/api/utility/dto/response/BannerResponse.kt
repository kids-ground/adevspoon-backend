package com.adevspoon.api.utility.dto.response

import com.adevspoon.domain.utility.dto.BannerInfo
import com.fasterxml.jackson.annotation.JsonProperty
import io.swagger.v3.oas.annotations.media.Schema

data class BannerResponse(
    @JsonProperty("banner_id")
    val bannerId: Long,
    val imageUrl: String,
    @Schema(description = "배너를 클릭했을 때 이동할 url")
    val url: String,
) {
    companion object {
        fun from(info: BannerInfo): BannerResponse {
            return BannerResponse(
                bannerId = info.bannerId,
                imageUrl = info.imageUrl,
                url = info.url
            )
        }
    }
}
