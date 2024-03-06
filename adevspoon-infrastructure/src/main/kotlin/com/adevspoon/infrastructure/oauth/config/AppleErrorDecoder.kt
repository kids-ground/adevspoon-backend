package com.adevspoon.infrastructure.oauth.config

import com.adevspoon.infrastructure.oauth.exception.OAuthErrorCode
import feign.Response
import feign.codec.ErrorDecoder
import java.lang.Exception

class AppleErrorDecoder: ErrorDecoder {
    override fun decode(methodKey: String?, response: Response?): Exception {
        return when(response?.status()) {
            in 400..< 600 -> OAuthErrorCode.APPLE_SERVER_ERROR.getException()
            else -> OAuthErrorCode.APPLE_SERVER_ERROR.getException()
        }
    }
}