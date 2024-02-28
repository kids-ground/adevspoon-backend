package com.adevspoon.infrastructure.config

import org.springframework.cloud.openfeign.EnableFeignClients
import org.springframework.context.annotation.Configuration

@Configuration
@EnableFeignClients(basePackages = ["com.adevspoon.infrastructure"])
class AdevspoonInfra