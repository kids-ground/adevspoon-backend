package com.adevspoon.api.config

import com.fasterxml.jackson.annotation.JsonInclude
import com.fasterxml.jackson.databind.ObjectMapper
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule
import jakarta.annotation.PostConstruct
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration

@Configuration
class AppConfig(
    val objectMapper: ObjectMapper
) {

    @PostConstruct
    fun setupObjectMapper() {
        objectMapper.setSerializationInclusion(JsonInclude.Include.NON_NULL)
    }
}