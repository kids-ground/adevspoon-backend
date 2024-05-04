package com.adevspoon.domain.member.service

import com.adevspoon.domain.common.annotation.DomainService
import com.adevspoon.domain.common.event.AnswerActivityEvent
import com.adevspoon.domain.common.event.AttendanceActivityEvent
import com.adevspoon.domain.common.event.BoardPostActivityEvent
import com.adevspoon.domain.member.domain.UserActivityEntity
import com.adevspoon.domain.member.domain.UserBadgeAcheiveEntity
import com.adevspoon.domain.member.domain.UserBadgeAcheiveId
import com.adevspoon.domain.member.exception.MemberNotFoundException
import com.adevspoon.domain.member.repository.*
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
    private val attendanceRepository: AttendanceRepository,
    private val badgeRepository: BadgeRepository,
) {
    @Async
    @TransactionalEventListener(phase = TransactionPhase.AFTER_COMMIT)
    @Transactional
    fun handleAttendanceEvent(event: AttendanceActivityEvent) {
        val updateAttendanceActivity = updateAttendanceActivity(event.memberId)
        acquireNewBadges(event.memberId, updateAttendanceActivity)
    }

    @Async
    @TransactionalEventListener(phase = TransactionPhase.AFTER_COMMIT)
    @Transactional
    fun handleAnswerEvent(event: AnswerActivityEvent) {
        val updatedUserActivity = updateAnswerActivity(event.memberId)
        acquireNewBadges(event.memberId, updatedUserActivity)
    }

    @Async
    @TransactionalEventListener(phase = TransactionPhase.AFTER_COMMIT)
    @Transactional
    fun handleBoardPostEvent(event: BoardPostActivityEvent) {
        userActivityRepository.increaseBoardPostCount(event.memberId)
    }

    private fun updateAttendanceActivity(memberId: Long): UserActivityEntity {
        return (userActivityRepository.findByIdWithLock(memberId) ?: throw MemberNotFoundException())
            .apply {
                val attendanceDateList = attendanceRepository.findAttendanceDateList(memberId)
                    .map { it.toLocalDate() }

                cumulativeAttendanceCount += 1
                consecutiveAttendanceCount =
                    if (attendanceDateList.size > 1 &&
                        attendanceDateList[0].until(attendanceDateList[1], ChronoUnit.DAYS) == 1L)
                        consecutiveAttendanceCount + 1
                    else 1
                maxConsecutiveAttendanceCount = maxOf(maxConsecutiveAttendanceCount, consecutiveAttendanceCount)
            }
    }

    private fun updateAnswerActivity(memberId: Long): UserActivityEntity {
        return (userActivityRepository.findByIdWithLock(memberId) ?: throw MemberNotFoundException())
            .apply {
                val answeredDateList = answerRepository.findAnsweredDateList(memberId)
                    .map { it.toLocalDate() }

                cumulativeAnswerCount += 1
                consecutiveAnswerCount =
                    if (answeredDateList.size > 1 &&
                        answeredDateList[0].until(answeredDateList[1], ChronoUnit.DAYS) == 1L)
                        consecutiveAnswerCount + 1
                    else 1
                maxConsecutiveAnswerCount = maxOf(maxConsecutiveAnswerCount, consecutiveAnswerCount)
            }
    }

    private fun acquireNewBadges(memberId: Long, userActivity: UserActivityEntity) {
        val user = userRepository.findByIdOrNull(memberId) ?: throw MemberNotFoundException()
        val notAchievedBadges = badgeRepository.findAll()
            .also { badgeList ->
                val achievedBadges = userBadgeAchieveRepository.findUserBadgeList(memberId)
                badgeList.removeAll { badge -> achievedBadges.any { badge.id == it.id } }
            }
        val newAchieveBadges = notAchievedBadges.filter {
            when(it.criteria) {
                userActivity::cumulativeAttendanceCount.name ->
                    (it.criteriaValue ?: 0) <= userActivity.cumulativeAttendanceCount
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