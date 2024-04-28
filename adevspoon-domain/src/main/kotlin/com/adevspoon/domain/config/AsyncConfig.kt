package com.adevspoon.domain.config

import org.springframework.context.annotation.Configuration
import org.springframework.core.Ordered
import org.springframework.scheduling.annotation.EnableAsync

@Configuration
@EnableAsync(order = Ordered.LOWEST_PRECEDENCE - 11) // 트랜잭션 AOP보다 먼저 처리
class AsyncConfig