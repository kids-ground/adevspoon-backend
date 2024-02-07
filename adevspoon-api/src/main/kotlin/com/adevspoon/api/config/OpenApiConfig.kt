package com.adevspoon.api.config

import io.swagger.v3.oas.models.Components
import io.swagger.v3.oas.models.OpenAPI
import io.swagger.v3.oas.models.info.Info
import io.swagger.v3.oas.models.security.SecurityRequirement
import io.swagger.v3.oas.models.security.SecurityScheme
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration

private const val SECURITY_SCHEME_NAME: String = "JWT 토큰"

@Configuration
class OpenApiConfig {
    @Bean
    fun openApi(): OpenAPI = OpenAPI()
        .info(apiInfo())
        .components(Components().addSecuritySchemes(SECURITY_SCHEME_NAME, apiSecurityScheme()))
        .security(apiSecurityRequirementList())

    private fun apiInfo() = Info()
        .title("Adevspoon API 명세")
        .description("Adevspoon API 명세서")
        .version("v1.0.0")

    private fun apiSecurityScheme() = SecurityScheme()
        .type(SecurityScheme.Type.HTTP).scheme("bearer").bearerFormat("JWT")
        .`in`(SecurityScheme.In.HEADER).name("Authorization")

    private fun apiSecurityRequirementList() = listOf(SecurityRequirement().addList(SECURITY_SCHEME_NAME))
}