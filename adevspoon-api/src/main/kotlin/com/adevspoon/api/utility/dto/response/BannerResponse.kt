package com.adevspoon.api.utility.dto.response

import com.fasterxml.jackson.annotation.JsonProperty
import io.swagger.v3.oas.annotations.media.Schema
import java.time.LocalDate

data class BannerResponse(
    @JsonProperty("banner_id")
    val bannerId: Long,
    val imageUrl: String,
    @Schema(description = "배너를 클릭했을 때 이동할 url")
    val url: String,
    @Schema(description = "배너 오픈 날짜", example = "2024-01-01")
    val openDate: LocalDate,
    @Schema(description = "배너 종료 날짜", example = "2024-01-10")
    val closeDate: LocalDate? = null
)
