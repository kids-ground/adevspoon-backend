package com.adevspoon.domain.common.lock.interfaces

import com.adevspoon.domain.common.lock.LockKey

interface DistributedLockRepository: ListableLockRepository {
    fun <R> withLock(key: LockKey, block: () -> R?): R?
    fun <R> withAllLock(keys: List<LockKey>, block: () -> R?): R?
}