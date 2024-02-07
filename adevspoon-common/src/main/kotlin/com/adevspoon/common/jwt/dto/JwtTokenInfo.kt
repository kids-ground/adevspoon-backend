package com.adevspoon.common.jwt.dto

data class JwtTokenInfo(
    val tokenType: JwtTokenType,
    val userId: Long,
    val authorities: String
)

enum class JwtTokenType{
    ACCESS, REFRESH
}