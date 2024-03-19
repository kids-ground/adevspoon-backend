package com.adevspoon.api.common.annotation

import io.swagger.v3.oas.annotations.media.Schema
import org.springframework.stereotype.Service


@Target(AnnotationTarget.VALUE_PARAMETER)
@Retention(AnnotationRetention.RUNTIME)
@MustBeDocumented
@Schema(hidden = true)
@Service
annotation class RequestUser()
