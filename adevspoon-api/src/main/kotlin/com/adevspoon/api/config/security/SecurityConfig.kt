package com.adevspoon.api.config.security

import com.adevspoon.api.common.utils.JwtProcessor
import org.slf4j.LoggerFactory
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.security.config.annotation.web.builders.HttpSecurity
import org.springframework.security.config.http.SessionCreationPolicy
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter
import org.springframework.web.cors.CorsConfiguration
import org.springframework.web.cors.UrlBasedCorsConfigurationSource

@Configuration
class SecurityConfig(
    private val jwtProcessor: JwtProcessor
) {
    private val allowedSwaggerUrls = arrayOf("/docs", "/swagger-ui/**", "/v3/**")
    private val allowedApiUrls = arrayOf("/member", "/dummy")
    private val log = LoggerFactory.getLogger(this.javaClass)!!

    @Bean
    fun filterChain(http: HttpSecurity) = http
        .csrf { it.disable() }
        .formLogin { it.disable() }
        .httpBasic { it.disable() }
        .sessionManagement { it.sessionCreationPolicy(SessionCreationPolicy.STATELESS) }
        .addFilterBefore(jwtTokenAuthenticationFilter(), UsernamePasswordAuthenticationFilter::class.java)
        .authorizeHttpRequests {
            it.requestMatchers(*allowedSwaggerUrls).permitAll()
                .requestMatchers(*allowedApiUrls).permitAll()
//                .requestMatchers(PathRequest.toH2Console()).permitAll()
                .anyRequest().authenticated()
        }
        .exceptionHandling {
            it.authenticationEntryPoint { request, response, authException ->
                log.info("인증불가 401")
                // 인증 불가 - 401
            }
            it.accessDeniedHandler { request, response, accessDeniedException ->
                log.info("권한없음 403")
                // 권한 없음 - 403
            }
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

    fun jwtTokenAuthenticationFilter() = JwtTokenAuthenticationFilter(jwtProcessor)
}