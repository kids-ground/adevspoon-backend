package com.adevspoon.api.member.dto.response

data class TokenResponse (
    val accessToken: String,
    val refreshToken: String,
)