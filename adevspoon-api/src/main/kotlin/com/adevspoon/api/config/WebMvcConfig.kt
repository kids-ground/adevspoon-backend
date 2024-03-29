package com.adevspoon.api.config

import com.adevspoon.api.config.controller.converter.StringToLegacyDtoEnumConverterFactory
import com.adevspoon.api.config.controller.handler.StringToSuccessResponseReturnValueHandler
import com.adevspoon.api.config.controller.resolver.RequestUserArgumentResolver
import org.springframework.context.annotation.Configuration
import org.springframework.format.FormatterRegistry
import org.springframework.web.method.support.HandlerMethodArgumentResolver
import org.springframework.web.method.support.HandlerMethodReturnValueHandler
import org.springframework.web.servlet.HandlerExceptionResolver
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer
import org.springframework.web.servlet.mvc.method.annotation.ExceptionHandlerExceptionResolver
import org.springframework.web.servlet.mvc.method.annotation.RequestResponseBodyMethodProcessor

@Configuration
class WebMvcConfig: WebMvcConfigurer {
    override fun addArgumentResolvers(resolvers: MutableList<HandlerMethodArgumentResolver>) {
        resolvers.add(RequestUserArgumentResolver())
    }

    override fun addFormatters(registry: FormatterRegistry) {
        registry.addConverterFactory(StringToLegacyDtoEnumConverterFactory())
    }

    override fun addReturnValueHandlers(handlers: MutableList<HandlerMethodReturnValueHandler>) {
        handlers.add(StringToSuccessResponseReturnValueHandler())
    }

    // String 공통응답처리를 위한 ReturnValueHandler에 delegate를 설정
    override fun extendHandlerExceptionResolvers(resolvers: MutableList<HandlerExceptionResolver>) {
        resolvers.firstOrNull { it is ExceptionHandlerExceptionResolver }
            ?.let { it as ExceptionHandlerExceptionResolver }
            ?.let { it.returnValueHandlers?.handlers }
            ?.also { handler ->
                val responseBodyHandler = handler.firstOrNull { it is RequestResponseBodyMethodProcessor }
                handler.firstOrNull { it is StringToSuccessResponseReturnValueHandler }
                    ?.let { it as StringToSuccessResponseReturnValueHandler }
                    ?.let {
                        it.delegate = responseBodyHandler
                    }
            }
    }

}