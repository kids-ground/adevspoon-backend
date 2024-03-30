package com.adevspoon.api.config

import com.adevspoon.api.config.controller.handler.StringLiteralReturnValueHandler
import org.springframework.context.ApplicationContext
import org.springframework.context.ApplicationContextAware
import org.springframework.context.annotation.Configuration
import org.springframework.web.method.support.HandlerMethodReturnValueHandler
import org.springframework.web.servlet.mvc.method.annotation.RequestMappingHandlerAdapter
import org.springframework.web.servlet.mvc.method.annotation.RequestResponseBodyMethodProcessor

@Configuration
class HandlerAdapterConfig (
    private val handlerAdapter: RequestMappingHandlerAdapter,
): ApplicationContextAware {
    override fun setApplicationContext(applicationContext: ApplicationContext) {
        addStringLiteralReturnValueHandler()
    }

    // StringLiteralReturnValueHandler 의존성 해결, 최우선순위로 등록
    private fun addStringLiteralReturnValueHandler() {
        val newReturnValueHandlers = mutableListOf<HandlerMethodReturnValueHandler>()
        val responseBodyReturnHandler = (handlerAdapter.returnValueHandlers
            ?.firstOrNull { it is RequestResponseBodyMethodProcessor }
            ?: return)
        newReturnValueHandlers.add(StringLiteralReturnValueHandler(responseBodyReturnHandler))
        newReturnValueHandlers.addAll(handlerAdapter.returnValueHandlers ?: emptyList())
        handlerAdapter.returnValueHandlers = newReturnValueHandlers
    }
}