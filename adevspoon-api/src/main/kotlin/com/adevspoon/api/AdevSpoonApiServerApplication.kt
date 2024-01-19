package com.adevspoon.api

import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication

@SpringBootApplication
class AdevSpoonApiServerApplication

fun main(args: Array<String>) {
    runApplication<AdevSpoonApiServerApplication>(*args)
}