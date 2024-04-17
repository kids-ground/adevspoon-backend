package com.adevspoon.domain.common.annotation

import com.adevspoon.domain.common.lock.LockKey
import kotlin.reflect.KClass


@Target(AnnotationTarget.FUNCTION)
@Retention(AnnotationRetention.RUNTIME)
annotation class DistributedLock(
    val keyClass: Array<KClass<out LockKey>> = [],
)
