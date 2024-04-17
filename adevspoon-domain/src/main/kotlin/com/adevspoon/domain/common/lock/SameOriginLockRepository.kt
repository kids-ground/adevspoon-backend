package com.adevspoon.domain.common.lock

import com.adevspoon.domain.common.annotation.LockDataSource
import com.adevspoon.domain.common.exception.DomainFailToGetLockException
import com.adevspoon.domain.common.exception.DomainFailToReleaseLockException
import com.adevspoon.domain.common.lock.interfaces.DistributedLockRepository
import org.slf4j.LoggerFactory
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate

private const val GET_LOCK = "SELECT GET_LOCK(:key, :timeout)"
private const val RELEASE_LOCK = "SELECT RELEASE_LOCK(:key)"


class SameOriginLockRepository(
    @LockDataSource private val jdbcTemplate: NamedParameterJdbcTemplate
): DistributedLockRepository {
    private val logger = LoggerFactory.getLogger(this.javaClass)!!

    override fun <R> withLock(key: LockKey, block: () -> R?): R? {
        try {
            getLock(key)
            return block()
        } finally {
            releaseLock(key)
        }
    }

    override fun <R> withAllLock(keys: List<LockKey>, block: () -> R?): R? {
        try {
            getAllLock(keys)
            return block()
        } finally {
            releaseAllLock(keys)
        }
    }

    override fun getAllLock(keys: List<LockKey>): Boolean {
        keys.map { getLock(it) }
        return true
    }

    override fun releaseAllLock(keys: List<LockKey>): Boolean {
        keys.map { releaseLock(it) }
        return true
    }

    override fun getLock(key: LockKey): Boolean {
        val params: MutableMap<String, Any?> = mutableMapOf( "key" to key.name, "timeout" to key.timeout)
        val result = jdbcTemplate.queryForObject(GET_LOCK, params, Int::class.java)
        return checkResult(result, key.name, LockType.GET)
    }

    override fun releaseLock(key: LockKey): Boolean {
        val params: MutableMap<String, Any?> = mutableMapOf( "key" to key.name)
        val result = jdbcTemplate.queryForObject(RELEASE_LOCK, params, Int::class.java)
        return checkResult(result, key.name, LockType.RELEASE)
    }

    private fun checkResult(
        result: Int?,
        key: String,
        type: LockType
    ): Boolean {
        when(result) {
            1 -> logger.info("Lock ${type.name} 성공 : $key")
            null -> {
                if (type == LockType.GET) {
                    logger.error("Lock ${type.name} 실패 : $key")
                    throw DomainFailToGetLockException(key, "에러 발생")
                }
            }
            else -> {
                if (type == LockType.GET) {
                    logger.error("Lock ${type.name} Timeout : $key, result : $result")
                    throw DomainFailToGetLockException(key, "타임아웃")
                } else {
                    logger.error("Lock ${type.name} Not OWNER : $key, result : $result")
                    throw DomainFailToReleaseLockException(key, "Key의 주인이 아님")
                }
            }
        }

        return true
    }

    enum class LockType {
        GET, RELEASE
    }
}