package com.adevspoon.domain.member.repository

import com.adevspoon.domain.member.domain.UserActivityEntity
import jakarta.persistence.LockModeType
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.data.jpa.repository.Lock
import org.springframework.data.jpa.repository.Modifying
import org.springframework.data.jpa.repository.Query

interface UserActivityRepository : JpaRepository<UserActivityEntity, Long> {
    @Modifying(clearAutomatically = true)
    @Query("UPDATE UserActivityEntity u SET u.boardPostCount = u.boardPostCount + 1 WHERE u.id = :userId")
    fun increaseBoardPostCount(userId: Long): Int

    @Lock(LockModeType.PESSIMISTIC_WRITE)
    @Query("SELECT u FROM UserActivityEntity u WHERE u.id = :userid")
    fun findByIdWithLock(userid: Long): UserActivityEntity?
}