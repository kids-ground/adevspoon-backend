package com.adevspoon.api

import org.slf4j.LoggerFactory
import org.springframework.http.ResponseEntity
import org.springframework.security.access.annotation.Secured
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

@RestController
@RequestMapping("/dummy")
class DummyController{
    private val log = LoggerFactory.getLogger(this.javaClass)!!
    @GetMapping
    fun dummyTest(): String {
        log.info("dummy ㅎ")
        return "dummy ㅎ2"
    }
}