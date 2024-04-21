package com.adevspoon.domain.member.service

import com.adevspoon.domain.common.annotation.DomainService
import com.adevspoon.domain.common.event.AnswerActivityEvent
import com.adevspoon.domain.common.event.AttendanceActivityEvent
import com.adevspoon.domain.common.event.BoardPostActivityEvent
import com.adevspoon.domain.member.repository.UserActivityRepository
import jakarta.transaction.Transactional
import org.springframework.scheduling.annotation.Async
import org.springframework.transaction.event.TransactionPhase
import org.springframework.transaction.event.TransactionalEventListener

@DomainService
class MemberActivityEventHandler(
    private val userActivityRepository: UserActivityRepository,
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
        TODO("""
            Implement the logic to handle the ANSWER event
        """.trimIndent())
    }

    @Async
    @TransactionalEventListener(phase = TransactionPhase.AFTER_COMMIT)
    @Transactional
    fun handleBoardPostEvent(event: BoardPostActivityEvent) {
        userActivityRepository.increaseBoardPostCount(event.memberId)
    }
}