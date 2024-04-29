package com.adevspoon.domain.member.service

import com.adevspoon.domain.common.annotation.DomainService
import com.adevspoon.domain.common.event.AnswerActivityEvent
import com.adevspoon.domain.common.event.AttendanceActivityEvent
import com.adevspoon.domain.common.event.BoardPostActivityEvent
import com.adevspoon.domain.member.domain.UserActivityEntity
import com.adevspoon.domain.member.domain.UserBadgeAcheiveEntity
import com.adevspoon.domain.member.domain.UserBadgeAcheiveId
import com.adevspoon.domain.member.exception.MemberNotFoundException
import com.adevspoon.domain.member.repository.BadgeRepository
import com.adevspoon.domain.member.repository.UserActivityRepository
import com.adevspoon.domain.member.repository.UserBadgeAchieveRepository
import com.adevspoon.domain.member.repository.UserRepository
import com.adevspoon.domain.techQuestion.repository.AnswerRepository
import jakarta.transaction.Transactional
import org.springframework.data.repository.findByIdOrNull
import org.springframework.scheduling.annotation.Async
import org.springframework.transaction.event.TransactionPhase
import org.springframework.transaction.event.TransactionalEventListener
import java.time.temporal.ChronoUnit

@DomainService
class MemberActivityEventHandler(
    private val userRepository: UserRepository,
    private val userActivityRepository: UserActivityRepository,
    private val userBadgeAchieveRepository: UserBadgeAchieveRepository,
    private val answerRepository: AnswerRepository,
    private val badgeRepository: BadgeRepository,
) {
    @Async
    @TransactionalEventListener(phase = TransactionPhase.AFTER_COMMIT)
    @Transactional
    fun handleAttendanceEvent(event: AttendanceActivityEvent) {
        TODO("""
            Implement the logic to handle the ATTENDANCE event
        """.trimIndent())
    }

    @Async
    @TransactionalEventListener(phase = TransactionPhase.AFTER_COMMIT)
    @Transactional
    fun handleAnswerEvent(event: AnswerActivityEvent) {
        val updatedUserActivity = updateUserActivity(event)
        acquireNewBadges(updatedUserActivity, event)
    }

    @Async
    @TransactionalEventListener(phase = TransactionPhase.AFTER_COMMIT)
    @Transactional
    fun handleBoardPostEvent(event: BoardPostActivityEvent) {
        userActivityRepository.increaseBoardPostCount(event.memberId)
    }

    private fun updateUserActivity(event: AnswerActivityEvent): UserActivityEntity {
        return userActivityRepository.findById(event.memberId)
            .orElseThrow { MemberNotFoundException() }
            .apply {
                val answeredDateList = answerRepository.findAnsweredDateList(event.memberId)
                    .map { it.toLocalDate() }

                cumulativeAnswerCount += 1
                consecutiveAnswerCount =
                    if (answeredDateList.size > 1 &&
                        answeredDateList[0].until(answeredDateList[1], ChronoUnit.DAYS) == 1L)
                        consecutiveAnswerCount + 1
                    else 1
                maxConsecutiveAnswerCount = maxOf(maxConsecutiveAnswerCount, consecutiveAnswerCount)
            }
            .let(userActivityRepository::save)
    }

    private fun acquireNewBadges(userActivity: UserActivityEntity, event: AnswerActivityEvent) {
        val user = userRepository.findByIdOrNull(event.memberId) ?: throw MemberNotFoundException()
        val notAchievedBadges = badgeRepository.findAll()
            .also { badgeList ->
                val achievedBadges = userBadgeAchieveRepository.findUserBadgeList(event.memberId)
                badgeList.removeAll { badge -> achievedBadges.any { badge.id == it.id } }
            }
        val newAchieveBadges = notAchievedBadges.filter {
            when(it.criteria) {
                userActivity::cumulativeAnswerCount.name ->
                    (it.criteriaValue ?: 0) <= userActivity.cumulativeAnswerCount
                userActivity::maxConsecutiveAnswerCount.name ->
                    (it.criteriaValue ?: 0) <= userActivity.maxConsecutiveAnswerCount
                else -> false
            }
        }

        userBadgeAchieveRepository.saveAll(
            newAchieveBadges.map { badge ->
                UserBadgeAcheiveEntity(UserBadgeAcheiveId(user = user, badge = badge))
            }
        )
    }
}