package com.adevspoon.api.common.properties

import org.springframework.boot.context.properties.ConfigurationProperties
import org.springframework.boot.context.properties.bind.ConstructorBinding

@ConfigurationProperties(prefix = "custom.image")
data class ImageProperties @ConstructorBinding constructor(
    val profileUrl: String,
    val thumbnailUrl: String,
)