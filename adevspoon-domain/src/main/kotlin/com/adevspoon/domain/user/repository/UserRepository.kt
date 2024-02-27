package com.adevspoon.domain.user.repository

import com.adevspoon.domain.user.domain.User
import com.adevspoon.domain.user.domain.enums.UserOAuth
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.data.jpa.repository.Query
import org.springframework.stereotype.Repository

interface UserRepository : JpaRepository<User, Long> {
    @Query("SELECT u FROM User u WHERE u.oAuth = :oAuth AND u.appleId = :appleId")
    fun findByOAuthAndAppleId(oAuth: UserOAuth, appleId: String): User?

    @Query("SELECT u FROM User u WHERE u.oAuth = :oAuth AND u.appleId = :kakaoId")
    fun findByOAuthAndKakaoId(oAuth: UserOAuth, kakaoId: Long): User?
}