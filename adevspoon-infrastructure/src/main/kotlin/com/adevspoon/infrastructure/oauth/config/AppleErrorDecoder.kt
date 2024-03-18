package com.adevspoon.infrastructure.oauth.config

import com.adevspoon.infrastructure.oauth.exception.OAuthAppleServerErrorException
import com.adevspoon.infrastructure.oauth.exception.OAuthErrorCode
import feign.Response
import feign.codec.ErrorDecoder
import java.lang.Exception

class AppleErrorDecoder: ErrorDecoder {
    override fun decode(methodKey: String?, response: Response?): Exception {
        return when(response?.status()) {
            in 400..< 600 -> OAuthAppleServerErrorException(response?.reason())
            else -> OAuthAppleServerErrorException(response?.reason())
        }
    }
}