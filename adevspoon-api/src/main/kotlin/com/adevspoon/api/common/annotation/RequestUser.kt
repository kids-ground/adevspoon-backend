package com.adevspoon.api.common.annotation

import org.springframework.stereotype.Service


@Target(AnnotationTarget.VALUE_PARAMETER)
@Retention(AnnotationRetention.RUNTIME)
@MustBeDocumented
@Service
annotation class RequestUser()
