package com.adevspoon.domain.common.lock.interfaces

import com.adevspoon.domain.common.lock.LockKey

interface LockRepository {
    fun <R> withLock(key: LockKey, block: () -> R?): R?
}