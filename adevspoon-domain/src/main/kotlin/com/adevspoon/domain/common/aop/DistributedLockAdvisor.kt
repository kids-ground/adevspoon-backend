package com.adevspoon.domain.common.aop

import com.adevspoon.domain.common.annotation.DistributedLock
import com.adevspoon.domain.common.exception.DomainLockKeyNotSetException
import com.adevspoon.domain.common.lock.LockKey
import com.adevspoon.domain.common.lock.interfaces.DistributedLockRepository
import org.aspectj.lang.ProceedingJoinPoint
import org.aspectj.lang.annotation.Around
import org.aspectj.lang.annotation.Aspect
import org.aspectj.lang.reflect.MethodSignature
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty
import org.springframework.core.annotation.Order
import org.springframework.stereotype.Component
import kotlin.reflect.KClass

@Aspect
@Component
@ConditionalOnProperty(prefix = "spring.lock-datasource", name = ["jdbc-url"])
@Order(1)
class DistributedLockAdvisor(
    private val lockRepository: DistributedLockRepository
) {
    @Around("@annotation(com.adevspoon.domain.common.annotation.DistributedLock)")
    fun atTarget(joinPoint: ProceedingJoinPoint): Any? {
        val lockAnnotation = getAnnotation(joinPoint)
        val lockKeyList = getLockKeyList(joinPoint.args, lockAnnotation.keyClass)

        return lockRepository.withAllLock(lockKeyList) {
            joinPoint.proceed()
        }
    }

    private fun getAnnotation(joinPoint: ProceedingJoinPoint) =
        (joinPoint.signature as MethodSignature).method
            .getAnnotation(DistributedLock::class.java)

    private fun getLockKeyList(arguments: Array<Any>, keyClasses: Array<KClass<out LockKey>>) =
        arguments.filterIsInstance<LockKey>()
            .filter { keyClasses.isEmpty() || keyClasses.contains(it::class) }
            .toList()
            .takeIf { it.isNotEmpty() }
            ?: throw DomainLockKeyNotSetException()
}