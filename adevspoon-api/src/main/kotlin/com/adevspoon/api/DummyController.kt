package com.adevspoon.api

import com.adevspoon.api.common.annotation.RequestUser
import com.adevspoon.api.common.dto.RequestUserInfo
import org.slf4j.LoggerFactory
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

@RestController
@RequestMapping("/dummy")
class DummyController{
    private val log = LoggerFactory.getLogger(this.javaClass)!!
    @GetMapping
    fun dummyTest(param: DummyParam, @RequestUser user: RequestUserInfo): String {
        log.info("dummy ㅎ $param")
        return "dummy ㅎ2"
    }

    @GetMapping("/security")
    fun dummySecurityTest(@RequestUser user: RequestUserInfo): String {
        return "${user.userId}"
    }
}


class DummyParam{
    var name: String? = null
    var age: Int? = null

    override fun toString(): String{
        return "name: $name, age: $age"
    }
}