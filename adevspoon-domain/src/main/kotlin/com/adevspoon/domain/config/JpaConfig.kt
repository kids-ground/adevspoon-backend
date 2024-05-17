package com.adevspoon.domain.config

import com.zaxxer.hikari.HikariDataSource
import org.springframework.boot.autoconfigure.domain.EntityScan
import org.springframework.boot.autoconfigure.jdbc.DataSourceProperties
import org.springframework.boot.context.properties.ConfigurationProperties
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.context.annotation.Primary
import org.springframework.core.Ordered
import org.springframework.data.jpa.repository.config.EnableJpaAuditing
import org.springframework.data.jpa.repository.config.EnableJpaRepositories
import org.springframework.transaction.annotation.EnableTransactionManagement
import javax.sql.DataSource


@Configuration
@EnableJpaAuditing
@EntityScan(basePackages = ["com.adevspoon.domain"])
@EnableJpaRepositories(basePackages = ["com.adevspoon.domain"])
// Transaction 전 후로 실행되는 AOP를 위해 직접 설정
@EnableTransactionManagement(order = Ordered.LOWEST_PRECEDENCE - 10)
class JpaConfig {

    @Bean
    @Primary
    @ConfigurationProperties("spring.datasource")
    fun mainDataSourceProperties(): DataSourceProperties {
        return DataSourceProperties()
    }

    @Bean
    @Primary
    fun dataSource(): DataSource {
        return mainDataSourceProperties()
            .initializeDataSourceBuilder()
            .type(HikariDataSource::class.java)
            .build()
    }
}