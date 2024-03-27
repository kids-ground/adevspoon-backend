package com.adevspoon.api.config.swagger

import com.adevspoon.api.common.annotation.SecurityIgnored
import io.swagger.v3.oas.models.Components
import io.swagger.v3.oas.models.OpenAPI
import io.swagger.v3.oas.models.Operation
import io.swagger.v3.oas.models.info.Info
import io.swagger.v3.oas.models.security.SecurityRequirement
import io.swagger.v3.oas.models.security.SecurityScheme
import org.springdoc.core.customizers.OperationCustomizer
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.web.method.HandlerMethod

private const val SECURITY_SCHEME_NAME: String = "JWT 토큰"

@Configuration
class OpenApiConfig {
    @Bean
    fun openApi(): OpenAPI = OpenAPI()
        .info(apiInfo())
        .components(
            Components().addSecuritySchemes(SECURITY_SCHEME_NAME, apiSecurityScheme())
        )
        .security(apiSecurityRequirementList())

    private fun apiInfo() = Info()
        .title("Adevspoon API 명세")
        .description("""
            Adevspoon(개발 한 스푼) 어플리케이션을 위한 API 명세서입니다.
            - 모든 API의 Path는 '/api'로 시작합니다.
        """.trimIndent())
        .version("v1.0.0")

    private fun apiSecurityScheme() = SecurityScheme()
        .type(SecurityScheme.Type.HTTP).scheme("bearer").bearerFormat("JWT")
        .`in`(SecurityScheme.In.HEADER).name("Authorization")

    private fun apiSecurityRequirementList() = listOf(SecurityRequirement().addList(SECURITY_SCHEME_NAME))

    @Bean
    fun customOperationCustomizer(): OperationCustomizer {
        return OperationCustomizer { operation: Operation, handlerMethod: HandlerMethod ->
            val hasSecurityIgnored = handlerMethod.hasMethodAnnotation(SecurityIgnored::class.java)
            if (hasSecurityIgnored) operation.security = emptyList()

            operation
        }
    }
}