package com.adevspoon.api.common.dto

import com.adevspoon.api.common.enums.ServiceRole

data class JwtTokenInfo(
    val tokenType: JwtTokenType,
    val userId: Long,
    val role: ServiceRole = ServiceRole.USER
)

enum class JwtTokenType{
    ACCESS, REFRESH
}