package com.adevspoon.domain.dummy

import com.adevspoon.domain.common.annotation.DomainService
import org.springframework.transaction.annotation.Transactional

@DomainService
class DummyInnerService {

    @Transactional
    fun dummyInnerService() {
        println("DummyInnerService")
        throw RuntimeException("DummyInnerService")
    }
}