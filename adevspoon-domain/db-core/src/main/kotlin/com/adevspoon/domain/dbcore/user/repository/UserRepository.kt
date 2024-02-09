package com.adevspoon.domain.dbcore.user.repository

import com.adevspoon.domain.dbcore.user.domain.User
import org.springframework.data.jpa.repository.JpaRepository

interface UserRepository : JpaRepository<User, Long> {
}