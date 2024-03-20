package com.adevspoon.api.dummy

import com.adevspoon.api.common.annotation.RequestUser
import com.adevspoon.api.common.annotation.SecurityIgnored
import com.adevspoon.api.common.dto.RequestUserInfo
import com.adevspoon.api.config.swagger.SWAGGER_TAG_EXAMPLE
import io.swagger.v3.oas.annotations.Operation
import io.swagger.v3.oas.annotations.tags.Tag
import org.slf4j.LoggerFactory
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

@RestController
@RequestMapping("/dummy")
@Tag(name = SWAGGER_TAG_EXAMPLE)
class DummyController{
    private val log = LoggerFactory.getLogger(this.javaClass)!!

    @Operation(summary = "non-security 테스트용", description = "Security 필요없음")
    @GetMapping
    @SecurityIgnored
    fun dummyTest(param: DummyParam): String {
        log.info("dummy ㅎ $param")
        return "dummy ㅎ2"
    }

    @Operation(summary = "security 테스트용", description = "Security 필요함")
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