package com.adevspoon.api.config.security

import com.adevspoon.api.common.extension.writeErrorResponse
import com.adevspoon.common.exception.AdevspoonException
import com.adevspoon.common.exception.CommonErrorCode
import com.fasterxml.jackson.databind.ObjectMapper
import jakarta.servlet.FilterChain
import jakarta.servlet.http.HttpServletRequest
import jakarta.servlet.http.HttpServletResponse
import org.springframework.web.filter.OncePerRequestFilter

class AuthenticationExceptionFilter(
    private val objectMapper: ObjectMapper
): OncePerRequestFilter() {

    override fun doFilterInternal(
        request: HttpServletRequest,
        response: HttpServletResponse,
        filterChain: FilterChain
    ) {
        try {
            filterChain.doFilter(request, response)
        } catch (e: AdevspoonException) {
            logger.info("Authentication Error - ${e.errorInfo}")
            response.writeErrorResponse(e.errorInfo, objectMapper)
        } catch (e: Exception) {
            logger.error("Authentication Unknown Error - ${e.message}")
            response.writeErrorResponse(CommonErrorCode.INTERNAL_SERVER_ERROR, objectMapper)
        }
    }
}