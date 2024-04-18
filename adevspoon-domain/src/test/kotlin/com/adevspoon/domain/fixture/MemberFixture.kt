package com.adevspoon.domain.fixture

import com.adevspoon.domain.member.domain.UserEntity

class MemberFixture {
    companion object {
        fun createMember(
            id: Long = 1,
            nickname: String = "nickname",
        ) = UserEntity(
            id = id,
            nickname = nickname,
        )
    }
}