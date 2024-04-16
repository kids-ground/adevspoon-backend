package com.adevspoon.domain.common.annotation

import com.adevspoon.domain.common.lock.DistributedLockKey
import org.springframework.core.annotation.Order
import kotlin.reflect.KClass


@Target(AnnotationTarget.FUNCTION)
@Retention(AnnotationRetention.RUNTIME)
@Order(1)
annotation class DistributedLock(
    val keyClass: Array<KClass<out DistributedLockKey>> = [],
)
