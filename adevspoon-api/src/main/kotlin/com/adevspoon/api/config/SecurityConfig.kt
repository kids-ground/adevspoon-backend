package com.adevspoon.api.config

import org.springframework.boot.autoconfigure.security.servlet.PathRequest
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.security.config.annotation.web.builders.HttpSecurity
import org.springframework.security.config.http.SessionCreationPolicy

@Configuration
class SecurityConfig {

    private val allowedSwaggerUrls = arrayOf("/docs", "/swagger-ui/**", "/v3/**")
    private val allowedApiUrls = arrayOf("/")

    @Bean
    fun filterChain(http: HttpSecurity) = http
        .csrf { it.disable() }
        .formLogin { it.disable() }
        .httpBasic { it.disable() }
        .exceptionHandling {
            it.authenticationEntryPoint { request, response, authException ->
                // 인증 불가 - 401
            }
            it.accessDeniedHandler { request, response, accessDeniedException ->
                // 권한 없음 - 403
            }
        }
        .authorizeHttpRequests {
            it.requestMatchers(*allowedSwaggerUrls).permitAll()
                .requestMatchers(*allowedApiUrls).permitAll()
                .requestMatchers(PathRequest.toH2Console()).permitAll()
                .anyRequest().authenticated()
        }
        .sessionManagement { it.sessionCreationPolicy(SessionCreationPolicy.STATELESS) }
        .build()!!
}