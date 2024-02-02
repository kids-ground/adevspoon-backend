package com.adevspoon.api

import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.context.properties.ConfigurationPropertiesScan
import org.springframework.boot.runApplication

@ConfigurationPropertiesScan
@SpringBootApplication(scanBasePackages = ["com.adevspoon.api", "com.adevspoon.domain"])
class AdevSpoonApiServerApplication

fun main(args: Array<String>) {
    runApplication<AdevSpoonApiServerApplication>(*args)
}