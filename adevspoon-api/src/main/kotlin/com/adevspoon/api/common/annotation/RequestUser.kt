package com.adevspoon.api.common.annotation

import io.swagger.v3.oas.annotations.media.Schema


@Target(AnnotationTarget.VALUE_PARAMETER)
@Retention(AnnotationRetention.RUNTIME)
@MustBeDocumented
@Schema(hidden = true)
annotation class RequestUser()
