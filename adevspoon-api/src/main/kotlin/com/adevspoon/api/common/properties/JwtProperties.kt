package com.adevspoon.api.common.properties

import org.springframework.boot.context.properties.ConfigurationProperties
import org.springframework.boot.context.properties.bind.ConstructorBinding


@ConfigurationProperties(prefix = "jwt")
data class JwtProperties @ConstructorBinding constructor(
    val secretKey: String,
    val accessExpirationHours: Long,
    val refreshExpirationHours: Long,
    val issuer: String
)
