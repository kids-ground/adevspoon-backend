package com.adevspoon.domain.member.repository

import com.adevspoon.domain.member.domain.BadgeEntity
import com.adevspoon.domain.member.domain.UserBadgeAcheiveEntity
import com.adevspoon.domain.member.domain.UserBadgeAcheiveId
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.data.jpa.repository.Query

interface UserBadgeAcheiveRepository: JpaRepository<UserBadgeAcheiveEntity, UserBadgeAcheiveId> {
    @Query(
        "SELECT uba.id.badge " +
                "FROM UserBadgeAcheiveEntity uba " +
        "WHERE uba.id.user.id = :userId"
    )
    fun findUserBadgeList(userId: Long): List<BadgeEntity>
}