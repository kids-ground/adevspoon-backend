package com.adevspoon.domain.common.aop

import com.adevspoon.domain.common.annotation.DistributedLock
import com.adevspoon.domain.common.exception.DomainFailToGetLockException
import com.adevspoon.domain.common.lock.DistributedLockKey
import com.adevspoon.domain.common.lock.DistributedLockRepository
import org.aspectj.lang.ProceedingJoinPoint
import org.aspectj.lang.annotation.Around
import org.aspectj.lang.annotation.Aspect
import org.aspectj.lang.reflect.MethodSignature
import org.slf4j.Logger
import org.slf4j.LoggerFactory
import org.springframework.stereotype.Component
import kotlin.reflect.KClass

@Aspect
@Component
class DistributedLockAdvisor(
    private val lockRepository: DistributedLockRepository
) {
    val logger: Logger = LoggerFactory.getLogger(this.javaClass)

    @Around("@annotation(com.adevspoon.domain.common.annotation.DistributedLock)")
    fun atTarget(joinPoint: ProceedingJoinPoint): Any? {
        val lockAnnotation = (joinPoint.signature as MethodSignature).method
            .getAnnotation(DistributedLock::class.java)
        val lockKeyList = getLockKeyList(joinPoint.args, lockAnnotation.keyClass)

        try {
            lockKeyList.map { lockRepository.getLock(it.lockKey, it.timeout, it.leaseTime) }
                .filter { !it }
                .takeIf { it.isEmpty() }
                ?: throw DomainFailToGetLockException()
            logger.info("DistributedLock Get : ${lockKeyList.joinToString(", ") { it.lockKey }}")
            return joinPoint.proceed()
        } finally {
            logger.info("DistributedLock Release : ${lockKeyList.joinToString(", ") { it.lockKey }}")
            lockKeyList.forEach { lockRepository.releaseLock(it.lockKey) }
        }
    }

    private fun getLockKeyList(arguments: Array<Any>, keyClasses: Array<KClass<out DistributedLockKey>>) =
        arguments.filterIsInstance<DistributedLockKey>()
            .filter { keyClasses.isEmpty() || keyClasses.contains(it::class) }
            .toList()
}