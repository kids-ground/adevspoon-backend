package com.adevspoon.domain.common.lock

interface DistributedLockRepository {
    fun getLock(lockKey: String, timeout: Int, leaseTime: Int = -1): Boolean
    fun releaseLock(lockKey: String): Boolean
}