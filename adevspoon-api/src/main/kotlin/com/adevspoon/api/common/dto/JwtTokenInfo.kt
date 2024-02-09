package com.adevspoon.api.common.dto

data class JwtTokenInfo(
    val tokenType: JwtTokenType,
    val userId: Long,
    val authorities: String
)

enum class JwtTokenType{
    ACCESS, REFRESH
}