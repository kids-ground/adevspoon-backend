package com.adevspoon.common.jwt.dto

data class JwtTokenInfo(
    val userId: Long,
    val authorities: String
)
