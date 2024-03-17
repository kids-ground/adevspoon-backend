package com.adevspoon.domain.member.repository

import com.adevspoon.domain.annotation.RepositoryTest
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Test
import org.springframework.beans.factory.annotation.Autowired


@RepositoryTest
class UserBadgeAcheiveRepositoryTest {
    @Autowired
    private lateinit var userBadgeAcheiveRepository: UserBadgeAcheiveRepository

    @Test
    fun findUserBadgeList() {
        val userId = 1L
        val badgeList = userBadgeAcheiveRepository.findUserBadgeList(userId)
        badgeList.forEach {
            println(it.id)
        }
        assertEquals(0, badgeList.size)
    }
}