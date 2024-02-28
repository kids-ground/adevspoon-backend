package com.adevspoon.api.common.annotation

import org.springframework.core.annotation.AliasFor
import org.springframework.stereotype.Service


@Target(AnnotationTarget.TYPE, AnnotationTarget.CLASS)
@Retention(AnnotationRetention.RUNTIME)
@MustBeDocumented
@Service
annotation class Facade(
    @get:AliasFor(annotation = Service::class)
    val value: String = ""
)
