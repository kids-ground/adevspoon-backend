package com.adevspoon.domain.dummy

import com.adevspoon.domain.common.annotation.DomainService
import org.springframework.transaction.annotation.Transactional

@DomainService
class DummyOuterService(
    private val dummyInnerService: DummyInnerService
) {

    @Transactional
    fun dummyOuterService(): String {
        try {
            dummyInnerService.dummyInnerService()
        } catch (e: Exception) {
            println("DummyOuterService")
            return "Bad"
        }

        return "Good"
    }
}