package com.adevspoon.domain.fixture

import com.adevspoon.domain.member.domain.UserEntity

class MemberFixture {
    companion object {
        fun createMember(
            id: Long? = null,
            nickname: String = "nickname",
        ) = UserEntity(
            id = id ?: 1,
            nickname = nickname,
        )
    }
}