package com.adevspoon.api.common.dto

import com.adevspoon.api.common.enums.ServiceRole
import org.springframework.security.core.authority.SimpleGrantedAuthority

data class JwtTokenInfo(
    val tokenType: JwtTokenType,
    val userId: Long,
    val role: ServiceRole = ServiceRole.USER
) {
    fun getAuthority() = SimpleGrantedAuthority("ROLE_$role")
}

enum class JwtTokenType{
    ACCESS, REFRESH
}