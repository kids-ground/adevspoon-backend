package com.adevspoon.batch

import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication

@SpringBootApplication(scanBasePackages = ["com.adevspoon"])
class AdevspoonBatchServerApplication

fun main(args: Array<String>) {
    runApplication<AdevspoonBatchServerApplication>(*args)
}