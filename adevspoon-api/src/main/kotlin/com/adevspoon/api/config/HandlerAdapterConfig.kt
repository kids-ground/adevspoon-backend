package com.adevspoon.api.config

import com.adevspoon.api.config.controller.handler.StringToSuccessResponseReturnValueHandler
import org.springframework.context.ApplicationContext
import org.springframework.context.ApplicationContextAware
import org.springframework.context.annotation.Configuration
import org.springframework.web.method.support.HandlerMethodReturnValueHandler
import org.springframework.web.servlet.mvc.method.annotation.RequestMappingHandlerAdapter

@Configuration
class HandlerAdapterConfig (
    private val handlerAdapter: RequestMappingHandlerAdapter,
): ApplicationContextAware {
    override fun setApplicationContext(applicationContext: ApplicationContext) {
        changeReturnValueHandlersOrder()
    }

    fun changeReturnValueHandlersOrder() {
        val newReturnValueHandlers = mutableListOf<HandlerMethodReturnValueHandler>()
        handlerAdapter.returnValueHandlers
            ?.forEach {
                if (it is StringToSuccessResponseReturnValueHandler) {
                    newReturnValueHandlers.add(0, it)
                } else {
                    newReturnValueHandlers.add(it)
                }
            }

        handlerAdapter.returnValueHandlers = newReturnValueHandlers
        println("HandlerAdapter: $handlerAdapter")
    }
}