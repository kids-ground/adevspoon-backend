package com.adevspoon.api

import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

@RestController
@RequestMapping("/dummy")
class DummyController{

    @GetMapping
    fun dummyTest(): ResponseEntity<String> {
        return ResponseEntity.ok().body("dummy ㅎ2")
    }
}