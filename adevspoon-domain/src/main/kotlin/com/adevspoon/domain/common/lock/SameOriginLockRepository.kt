package com.adevspoon.domain.common.lock

import org.springframework.stereotype.Component
import javax.sql.DataSource

@Component
class SameOriginLockRepository(
    private val datasource: DataSource
): DistributedLockRepository {
    override fun getLock(lockKey: String, timeout: Int, leaseTime: Int): Boolean {
        TODO("Not yet implemented")
    }

    override fun releaseLock(lockKey: String): Boolean {
        TODO("Not yet implemented")
    }
}