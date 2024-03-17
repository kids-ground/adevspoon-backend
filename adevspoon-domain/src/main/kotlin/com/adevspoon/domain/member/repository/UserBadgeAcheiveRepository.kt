package com.adevspoon.domain.member.repository

import com.adevspoon.domain.member.domain.Badge
import com.adevspoon.domain.member.domain.UserBadgeAcheive
import com.adevspoon.domain.member.domain.UserBadgeAcheiveId
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.data.jpa.repository.Query

interface UserBadgeAcheiveRepository: JpaRepository<UserBadgeAcheive, UserBadgeAcheiveId> {
    @Query(
        "SELECT uba.id.badge " +
        "FROM UserBadgeAcheive uba " +
        "WHERE uba.id.user.id = :userId"
    )
    fun findUserBadgeList(userId: Long): List<Badge>
}