package com.adevspoon.domain.config

import org.springframework.boot.autoconfigure.domain.EntityScan
import org.springframework.context.annotation.Configuration
import org.springframework.data.jpa.repository.config.EnableJpaAuditing
import org.springframework.data.jpa.repository.config.EnableJpaRepositories


@Configuration
@EnableJpaAuditing
@EntityScan(basePackages = ["com.adevspoon.domain"])
@EnableJpaRepositories(basePackages = ["com.adevspoon.domain"])
class JpaConfig