package com.adevspoon.domain.user.repository

import com.adevspoon.domain.user.domain.User
import org.springframework.data.jpa.repository.JpaRepository

interface UserRepository : JpaRepository<User, Long> {
}