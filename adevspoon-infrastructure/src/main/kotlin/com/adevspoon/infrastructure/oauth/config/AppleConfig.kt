package com.adevspoon.infrastructure.oauth.config

import feign.codec.ErrorDecoder
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Import


@Import(AppleErrorDecoder::class)
class AppleConfig {

    @Bean
    @ConditionalOnMissingBean(value = [ErrorDecoder::class])
    fun errorDecoder() = AppleErrorDecoder()
}