package com.adevspoon.api.config.controllerAdvice

import com.adevspoon.common.dto.SuccessResponse
import org.springframework.core.MethodParameter
import org.springframework.http.HttpStatus
import org.springframework.http.MediaType
import org.springframework.http.converter.HttpMessageConverter
import org.springframework.http.server.ServerHttpRequest
import org.springframework.http.server.ServerHttpResponse
import org.springframework.http.server.ServletServerHttpResponse
import org.springframework.web.bind.annotation.RestControllerAdvice
import org.springframework.web.servlet.mvc.method.annotation.ResponseBodyAdvice


@RestControllerAdvice(basePackages = ["com.adevspoon"])
class ResponseAdviceHandler: ResponseBodyAdvice<Any> {
    override fun supports(returnType: MethodParameter, converterType: Class<out HttpMessageConverter<*>>): Boolean {
        return true
    }

    override fun beforeBodyWrite(
        body: Any?,
        returnType: MethodParameter,
        selectedContentType: MediaType,
        selectedConverterType: Class<out HttpMessageConverter<*>>,
        request: ServerHttpRequest,
        response: ServerHttpResponse
    ): Any? {
        val servletResponse = (response as ServletServerHttpResponse).servletResponse
        val status = servletResponse.status
        val resolve = HttpStatus.resolve(status) ?: return body

        return if (resolve.is2xxSuccessful) {
            SuccessResponse(data = body)
        } else body
    }
}