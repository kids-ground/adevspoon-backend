package com.adevspoon.domain.common.lock.interfaces

import com.adevspoon.domain.common.lock.LockKey

interface ListableLockRepository: LockRepository {
    fun getAllLock(keys: List<LockKey>): Boolean
    fun releaseAllLock(keys: List<LockKey>): Boolean
}