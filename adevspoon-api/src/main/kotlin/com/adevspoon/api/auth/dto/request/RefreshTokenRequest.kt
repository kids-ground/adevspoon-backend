package com.adevspoon.api.auth.dto.request

data class RefreshTokenRequest(
    val refreshToken: String,
    val accessToken: String,
)
