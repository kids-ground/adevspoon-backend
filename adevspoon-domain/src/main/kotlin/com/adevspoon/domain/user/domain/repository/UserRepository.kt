package com.adevspoon.domain.user.domain.repository

import com.adevspoon.domain.user.domain.User
import org.springframework.data.jpa.repository.JpaRepository

interface UserRepository : JpaRepository<User, Long> {
}