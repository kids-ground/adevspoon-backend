package com.adevspoon.api.auth.dto.response

import io.swagger.v3.oas.annotations.media.Schema

data class TokenResponse (
    @Schema(description = "새로 발급 받은 accessToken", example = "123.123.123")
    val accessToken: String,
    @Schema(description = "새로 발급 받은 refreshToken", example = "123.123.123")
    val refreshToken: String,
)