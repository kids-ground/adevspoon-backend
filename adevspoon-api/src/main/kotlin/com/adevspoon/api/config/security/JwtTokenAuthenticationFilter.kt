package com.adevspoon.api.config.security

import com.adevspoon.api.common.dto.RequestUserInfo
import com.adevspoon.api.common.utils.JwtProcessor
import jakarta.servlet.FilterChain
import jakarta.servlet.http.HttpServletRequest
import jakarta.servlet.http.HttpServletResponse
import org.slf4j.LoggerFactory
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken
import org.springframework.security.core.Authentication
import org.springframework.security.core.context.SecurityContextHolder
import org.springframework.security.core.userdetails.UserDetails
import org.springframework.web.filter.OncePerRequestFilter
import java.util.*

class JwtTokenAuthenticationFilter(
    private val jwtProcessor: JwtProcessor
): OncePerRequestFilter() {
    override fun doFilterInternal(
        request: HttpServletRequest,
        response: HttpServletResponse,
        filterChain: FilterChain
    ) {
        val authHeader = request.getHeader("Authorization")
        if (authHeader == null) {
            filterChain.doFilter(request, response)
            return
        }

        val tokenInfo = jwtProcessor.validateAuthorizationHeader(authHeader)
        SecurityContextHolder.getContext().authentication = UsernamePasswordAuthenticationToken(
            RequestUserInfo(tokenInfo.userId),
            null,
            Collections.singleton(tokenInfo.getAuthority())
        )

        filterChain.doFilter(request, response)
    }
}