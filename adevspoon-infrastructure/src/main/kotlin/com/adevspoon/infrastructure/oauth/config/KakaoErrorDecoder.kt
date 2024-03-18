package com.adevspoon.infrastructure.oauth.config

import com.adevspoon.infrastructure.oauth.exception.OAuthErrorCode
import com.adevspoon.infrastructure.oauth.exception.OAuthKakaoServerErrorException
import com.adevspoon.infrastructure.oauth.exception.OAuthKakaoTokenInvalidException
import feign.Response
import feign.codec.ErrorDecoder
import org.slf4j.LoggerFactory
import java.lang.Exception

class KakaoErrorDecoder: ErrorDecoder {
    private val logger = LoggerFactory.getLogger(this.javaClass)!!

    override fun decode(methodKey: String?, response: Response?): Exception {
        return when(response?.status()) {
            in 400..< 500 -> OAuthKakaoTokenInvalidException()
            else -> {
                logger.error("Kakao Origin Server Error: ${response?.status()} ${response?.reason()}")
                OAuthKakaoServerErrorException(response?.reason())
            }
        }
    }
}