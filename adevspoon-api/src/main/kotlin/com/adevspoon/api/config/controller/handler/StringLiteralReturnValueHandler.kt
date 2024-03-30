package com.adevspoon.api.config.controller.handler

import com.adevspoon.common.dto.SuccessResponse
import org.springframework.core.MethodParameter
import org.springframework.core.annotation.AnnotatedElementUtils
import org.springframework.web.bind.annotation.ResponseBody
import org.springframework.web.context.request.NativeWebRequest
import org.springframework.web.method.support.HandlerMethodReturnValueHandler
import org.springframework.web.method.support.ModelAndViewContainer

class StringLiteralReturnValueHandler(
    private val delegate: HandlerMethodReturnValueHandler
): HandlerMethodReturnValueHandler {

    override fun supportsReturnType(returnType: MethodParameter): Boolean {
        return returnType.parameterType == String::class.java &&
                (
                    AnnotatedElementUtils.hasAnnotation(
                        returnType.containingClass,
                        ResponseBody::class.java
                    ) || returnType.hasMethodAnnotation(ResponseBody::class.java)
                )
    }

    override fun handleReturnValue(
        returnValue: Any?,
        returnType: MethodParameter,
        mavContainer: ModelAndViewContainer,
        webRequest: NativeWebRequest
    ) {
        val realReturnValue = SuccessResponse(message = returnValue as String, data = null)
        delegate.handleReturnValue(realReturnValue, returnType, mavContainer, webRequest)
    }
}