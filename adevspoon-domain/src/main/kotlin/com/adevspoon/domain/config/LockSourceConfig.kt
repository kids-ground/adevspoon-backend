package com.adevspoon.domain.config

import com.adevspoon.domain.common.annotation.LockDataSource
import com.adevspoon.domain.common.lock.SameOriginLockRepository
import com.adevspoon.domain.common.lock.interfaces.DistributedLockRepository
import com.zaxxer.hikari.HikariConfig
import com.zaxxer.hikari.HikariDataSource
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty
import org.springframework.boot.context.properties.ConfigurationProperties
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import javax.sql.DataSource


@Configuration
@ConditionalOnProperty(prefix = "spring.lock-datasource", name = ["jdbc-url"])
class LockSourceConfig {

    @Bean
    @ConfigurationProperties("spring.lock-datasource")
    fun lockHikariConfig(): HikariConfig {
        return HikariConfig()
    }

    @Bean
    @LockDataSource
    fun lockDataSource(): DataSource {
        return HikariDataSource(lockHikariConfig())
    }

    @Bean
    fun lockRepository(): DistributedLockRepository {
        return SameOriginLockRepository(lockDataSource())
    }
}