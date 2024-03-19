package com.adevspoon.api.config.security

import com.adevspoon.api.common.extension.writeErrorResponse
import com.adevspoon.api.common.util.JwtProcessor
import com.adevspoon.common.exception.AuthErrorCode
import com.adevspoon.common.exception.CommonErrorCode
import com.fasterxml.jackson.databind.ObjectMapper
import org.slf4j.LoggerFactory
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.http.HttpMethod
import org.springframework.security.config.annotation.web.builders.HttpSecurity
import org.springframework.security.config.http.SessionCreationPolicy
import org.springframework.security.web.AuthenticationEntryPoint
import org.springframework.security.web.access.AccessDeniedHandler
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter
import org.springframework.web.cors.CorsConfiguration
import org.springframework.web.cors.UrlBasedCorsConfigurationSource

@Configuration
class SecurityConfig(
    private val jwtProcessor: JwtProcessor,
    private val objectMapper: ObjectMapper
) {
    private val allowedSwaggerUrls = arrayOf("/docs", "/swagger-ui/**", "/v3/**")
    private val log = LoggerFactory.getLogger(this.javaClass)!!

    @Bean
    fun filterChain(http: HttpSecurity) = http
        .csrf { it.disable() }
        .formLogin { it.disable() }
        .httpBasic { it.disable() }
        .sessionManagement { it.sessionCreationPolicy(SessionCreationPolicy.STATELESS) }
        .addFilterBefore(jwtTokenAuthenticationFilter(), UsernamePasswordAuthenticationFilter::class.java)
        .addFilterBefore(authenticationExceptionFilter(), JwtTokenAuthenticationFilter::class.java)
        .authorizeHttpRequests {
            it.requestMatchers(*allowedSwaggerUrls).permitAll()
                .requestMatchers(HttpMethod.POST, "/member").permitAll()
                .requestMatchers(HttpMethod.GET, "/dummy").permitAll()
                .anyRequest().authenticated()
        }
        .exceptionHandling {
            it.authenticationEntryPoint(authenticationEntryPoint())
            it.accessDeniedHandler(accessDeniedHandler())
        }
        .build()!!

    @Bean
    fun corsConfigurationSource(): UrlBasedCorsConfigurationSource {
        val corsConfigSource = UrlBasedCorsConfigurationSource()
        val corsConfig = CorsConfiguration()
        corsConfig.allowedHeaders = listOf("*")
        corsConfig.allowedMethods = listOf("HEAD", "POST", "GET", "DELETE", "PUT", "PATCH", "OPTIONS")
        corsConfig.allowedOrigins = listOf("*")
        corsConfig.allowCredentials = true
        corsConfig.maxAge = 3600L
        corsConfigSource.registerCorsConfiguration("/**", corsConfig)
        return corsConfigSource
    }

    private fun jwtTokenAuthenticationFilter() = JwtTokenAuthenticationFilter(jwtProcessor)

    private fun authenticationExceptionFilter() = AuthenticationExceptionFilter(objectMapper)

    private fun authenticationEntryPoint() = AuthenticationEntryPoint { request, response, authException ->
        log.info("Authentication Error - ${authException.message}")
        response.writeErrorResponse(AuthErrorCode.MISSING_AUTH, objectMapper)
    }

    private fun accessDeniedHandler() = AccessDeniedHandler { request, response, accessDeniedException ->
        log.info("Access Denied Error - ${accessDeniedException.message}")
        response.writeErrorResponse(CommonErrorCode.FORBIDDEN, objectMapper)
    }
}