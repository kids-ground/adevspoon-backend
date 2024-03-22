package com.adevspoon.api.dummy

import com.adevspoon.api.common.annotation.RequestUser
import com.adevspoon.api.common.annotation.SecurityIgnored
import com.adevspoon.api.common.dto.LegacyDtoEnum
import com.adevspoon.api.common.dto.RequestUserInfo
import com.adevspoon.api.config.swagger.SWAGGER_TAG_EXAMPLE
import io.swagger.v3.oas.annotations.Operation
import io.swagger.v3.oas.annotations.tags.Tag
import org.slf4j.LoggerFactory
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

@RestController
@RequestMapping("/dummy")
@Tag(name = SWAGGER_TAG_EXAMPLE)
class DummyController{
    private val log = LoggerFactory.getLogger(this.javaClass)!!

    @Operation(summary = "non-security GET 테스트용", description = "Security 필요없음")
    @GetMapping
    @SecurityIgnored
    fun dummyTest(param: DummyQueryRequest): DummyResponse {
        log.info("dummy ㅎ $param")
        return DummyResponse("dummy", DummyEnum.DUMMY1)
    }

    @Operation(summary = "non-security POST 테스트용", description = "Security 필요없음")
    @PostMapping
    @SecurityIgnored
    fun dummyPostTest(@RequestBody request: DummyRequest): DummyResponse {
        log.info("dummy ㅎ $request")
        return DummyResponse("dummy", DummyEnum.DUMMY1)
    }

    @Operation(summary = "security 테스트용", description = "Security 필요함")
    @GetMapping("/security")
    fun dummySecurityTest(@RequestUser user: RequestUserInfo): String {
        return "${user.userId}"
    }
}


data class DummyRequest(
    var name: String? = null,
    var enum: DummyEnum? = null,
)

data class DummyQueryRequest(
    var name: String? = null,
    var enum: DummyEnum? = null,
)

data class DummyResponse(
    var name: String,
    var enum: DummyEnum
)

enum class DummyEnum: LegacyDtoEnum {
    DUMMY1, DUMMY2, DUMMY3
}