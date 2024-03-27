package com.adevspoon.api.config

import com.adevspoon.api.config.controller.converter.StringToLegacyDtoEnumConverterFactory
import com.adevspoon.api.config.controller.resolver.RequestUserArgumentResolver
import org.springframework.context.annotation.Configuration
import org.springframework.format.FormatterRegistry
import org.springframework.web.method.support.HandlerMethodArgumentResolver
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer

@Configuration
class WebMvcConfig: WebMvcConfigurer {
    override fun addArgumentResolvers(resolvers: MutableList<HandlerMethodArgumentResolver>) {
        resolvers.add(RequestUserArgumentResolver())
    }

    override fun addFormatters(registry: FormatterRegistry) {
        registry.addConverterFactory(StringToLegacyDtoEnumConverterFactory())
    }
}