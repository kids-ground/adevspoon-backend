package com.adevspoon.domain.common.annotation

import org.springframework.beans.factory.annotation.Qualifier

@Target(AnnotationTarget.FIELD, AnnotationTarget.VALUE_PARAMETER, AnnotationTarget.FUNCTION)
@Retention(AnnotationRetention.RUNTIME)
@MustBeDocumented
@Qualifier("lockDataSource")
annotation class LockDataSource()
