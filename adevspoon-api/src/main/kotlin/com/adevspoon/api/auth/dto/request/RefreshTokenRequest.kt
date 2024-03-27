package com.adevspoon.api.auth.dto.request

import io.swagger.v3.oas.annotations.media.Schema
import jakarta.validation.constraints.NotBlank

data class RefreshTokenRequest(
    @Schema(description = "기존에 발급 받은 refreshToken", example = "123.123.123", nullable = false)
    @field:NotBlank(message = "refreshToken은 필수입니다")
    val refreshToken: String,

    @Schema(description = "기존에 발급 받은 accessToken", example = "123.123.123", nullable = false)
    @field:NotBlank(message = "accessToken은 필수입니다")
    val accessToken: String,
)
