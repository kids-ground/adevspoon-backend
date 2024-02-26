package com.adevspoon.infrastructure.oauth.dto

data class JwtHeader(
    val alg: String,
    val kid: String
)
