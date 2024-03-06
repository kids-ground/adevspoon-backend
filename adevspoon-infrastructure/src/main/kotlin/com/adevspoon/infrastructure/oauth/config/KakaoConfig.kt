package com.adevspoon.infrastructure.oauth.config

import feign.codec.ErrorDecoder
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Import


@Import(KakaoErrorDecoder::class)
class KakaoConfig {

    @Bean
    @ConditionalOnMissingBean(value = [ErrorDecoder::class])
    fun errorDecoder() = KakaoErrorDecoder()
}