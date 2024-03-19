package com.adevspoon.api.banner.dto

import com.fasterxml.jackson.annotation.JsonProperty
import java.time.LocalDate

data class BannerResponse(
    @JsonProperty("banner_id")
    val bannerId: Long,
    val imageUrl: String,
    val url: String,
    val openDate: LocalDate,
    val closeDate: LocalDate? = null
)