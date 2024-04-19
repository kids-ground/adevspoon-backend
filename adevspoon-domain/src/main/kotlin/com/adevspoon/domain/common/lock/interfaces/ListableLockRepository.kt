package com.adevspoon.domain.common.lock.interfaces

import com.adevspoon.domain.common.lock.LockKey

interface ListableLockRepository: LockRepository {
    fun <R> withAllLock(keys: List<LockKey>, block: () -> R?): R?
}