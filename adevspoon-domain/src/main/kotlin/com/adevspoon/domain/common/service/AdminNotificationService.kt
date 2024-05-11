package com.adevspoon.domain.common.service

import com.adevspoon.domain.common.annotation.DomainService
import com.adevspoon.domain.common.event.ReportEvent
import org.springframework.scheduling.annotation.Async
import org.springframework.transaction.annotation.Propagation
import org.springframework.transaction.annotation.Transactional
import org.springframework.transaction.event.TransactionPhase
import org.springframework.transaction.event.TransactionalEventListener

@DomainService
interface AdminNotificationService {
    @Async
    @TransactionalEventListener(phase = TransactionPhase.AFTER_COMMIT)
    @Transactional(propagation = Propagation.REQUIRES_NEW)
    fun sendReportNotification(event: ReportEvent)
}