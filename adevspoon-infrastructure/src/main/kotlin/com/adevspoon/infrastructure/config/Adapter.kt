package com.adevspoon.infrastructure.config

import org.springframework.core.annotation.AliasFor
import org.springframework.stereotype.Service

@Target(AnnotationTarget.TYPE, AnnotationTarget.CLASS)
@Retention(AnnotationRetention.RUNTIME)
@MustBeDocumented
@Service
annotation class Adapter(
    @get:AliasFor(annotation = Service::class)
    val value: String = ""
)
