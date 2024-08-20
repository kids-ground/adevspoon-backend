package com.adevspoon.domain.annotation

import io.mockk.junit5.MockKExtension
import org.junit.jupiter.api.extension.ExtendWith

@Target(AnnotationTarget.CLASS)
@Retention(AnnotationRetention.RUNTIME)
@MustBeDocumented
@ExtendWith(MockKExtension::class)
annotation class UnitTest()