package com.adevspoon.api.member.dto.request

import io.swagger.v3.oas.annotations.media.Schema
import jakarta.validation.constraints.Max
import jakarta.validation.constraints.Min

data class MemberActivityRequest(
    @Schema(description = "유저 아이디")
    val userId: Long,

    @Schema(description = "가져올 월(Month)", example = "7")
    @field:Min(1, message = "월은 1 이상이어야 합니다.")
    @field:Max(12, message = "월은 12 이하여야 합니다.")
    val month: Int,

    @Schema(description = "가져올 년도(Year)", example = "yyyy")
    @field:Min(2022, message = "년도는 2022 이상이어야 합니다.")
    val year: Int,
)