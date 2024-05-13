package com.adevspoon.api.config

import com.adevspoon.api.config.controller.handler.WrappingResponseBodyReturnValueHandler
import jakarta.annotation.PostConstruct
import org.springframework.context.annotation.Configuration
import org.springframework.web.method.support.HandlerMethodReturnValueHandler
import org.springframework.web.servlet.mvc.method.annotation.RequestMappingHandlerAdapter
import org.springframework.web.servlet.mvc.method.annotation.RequestResponseBodyMethodProcessor

@Configuration
class HandlerAdapterCustomConfig (
    private val handlerAdapter: RequestMappingHandlerAdapter,
) {
    @PostConstruct
    fun postConstruct() {
        addStringResponseBodyReturnValueHandler()
    }

    // 커스텀 ReturnValueHandler 의존성 해결, 최우선순위로 등록
    private fun addStringResponseBodyReturnValueHandler() {
        val newReturnValueHandlers = mutableListOf<HandlerMethodReturnValueHandler>()
        val responseBodyReturnHandler = (handlerAdapter.returnValueHandlers
            ?.firstOrNull { it is RequestResponseBodyMethodProcessor }
            ?: return)
        newReturnValueHandlers.add(WrappingResponseBodyReturnValueHandler(responseBodyReturnHandler))
        newReturnValueHandlers.addAll(handlerAdapter.returnValueHandlers ?: emptyList())
        handlerAdapter.returnValueHandlers = newReturnValueHandlers
    }
}