package com.adevspoon.domain.common.lock

import com.adevspoon.domain.common.annotation.LockDataSource
import com.adevspoon.domain.common.exception.DomainFailToGetLockException
import com.adevspoon.domain.common.exception.DomainFailToLockQueryException
import com.adevspoon.domain.common.exception.DomainFailToReleaseLockException
import com.adevspoon.domain.common.lock.interfaces.DistributedLockRepository
import org.slf4j.LoggerFactory
import java.sql.Connection
import java.sql.PreparedStatement
import javax.sql.DataSource



class SameOriginLockRepository(
    @LockDataSource private val dataSource: DataSource
): DistributedLockRepository {
    private val logger = LoggerFactory.getLogger(this.javaClass)!!

    override fun <R> withLock(key: LockKey, block: () -> R?): R? {
        dataSource.connection.use {
            try {
                getLock(it, key)
                return block()
            } finally {
                releaseLock(it, key)
            }
        }
    }

    override fun <R> withAllLock(keys: List<LockKey>, block: () -> R?): R? {
        if (keys.isEmpty()) return block()
        else if (keys.size == 1) return withLock(keys[0], block)

        dataSource.connection.use {connection ->
            try {
                keys.map { getLock(connection, it) }
                return block()
            } finally {
                releaseAllLock(connection, keys)
            }
        }
    }

    private fun getLock(connection: Connection, key: LockKey) {
        val getLockQuery = "SELECT GET_LOCK(?, ?)"

        connection.prepareStatement(getLockQuery).use { statement ->
            statement.setString(1, key.name)
            statement.setInt(2, key.timeout)
            checkResult(statement, LockType.GET, key.name)
        }
    }

    private fun releaseLock(connection: Connection, key: LockKey) {
        val releaseLockQuery = "SELECT RELEASE_LOCK(?)"

        connection.prepareStatement(releaseLockQuery).use { statement ->
            statement.setString(1, key.name)
            checkResult(statement, LockType.RELEASE, key.name)
        }
    }

    private fun releaseAllLock(connection: Connection, keys: List<LockKey>) {
        val releaseAllLocksQuery = "SELECT RELEASE_ALL_LOCKS()"

        connection.prepareStatement(releaseAllLocksQuery).use { statement ->
            statement.executeQuery().use { resultSet ->
                resultSet.next()
                checkResult(statement, LockType.RELEASE, *keys.map { it.name }.toTypedArray())
            }
        }
    }

    private fun checkResult(
        statement: PreparedStatement,
        type: LockType,
        vararg key: String,
    ) {
        statement.executeQuery().use { resultSet ->
            if (!resultSet.next()) {
                logger.error("Lock ${type.name} 실패 : $key")
                throw DomainFailToLockQueryException()
            }

            val result = resultSet.getInt(1)
            when(type) {
                LockType.GET -> {
                    if (result == 0) {
                        logger.error("Lock ${type.name} 실패 : $key")
                        throw DomainFailToGetLockException(key[0], "에러 발생")
                    }
                    logger.info("Lock ${type.name} 성공 : $key")
                }
                LockType.RELEASE -> {
                    if (result == 0) {
                        logger.error("Lock ${type.name} 실패 : $key")
                        throw DomainFailToReleaseLockException(key[0], "에러 발생")
                    }
                    logger.info("Lock ${type.name} 성공 : $key")
                }
                LockType.RELEASE_ALL -> {
                    if (result == 0) {
                        logger.error("Lock ${type.name} 실패 : $key")
                        throw DomainFailToReleaseLockException(key.joinToString(", "), "에러 발생")
                    }
                    logger.info("Lock ${type.name} 성공 : $key")
                }
            }
        }
    }

    enum class LockType {
        GET, RELEASE, RELEASE_ALL
    }
}