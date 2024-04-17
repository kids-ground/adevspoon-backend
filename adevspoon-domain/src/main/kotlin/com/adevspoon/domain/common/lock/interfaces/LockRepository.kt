package com.adevspoon.domain.common.lock.interfaces

import com.adevspoon.domain.common.lock.LockKey

interface LockRepository {
    fun getLock(key: LockKey): Boolean
    fun releaseLock(key: LockKey): Boolean
}