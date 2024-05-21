package com.adevspoon.domain.admin.service

import com.adevspoon.domain.common.annotation.DomainService
import com.adevspoon.domain.common.event.ReportEvent
import com.adevspoon.infrastructure.alarm.dto.AlarmType
import com.adevspoon.infrastructure.alarm.service.AlarmAdapter
import org.springframework.scheduling.annotation.Async
import org.springframework.transaction.event.TransactionPhase
import org.springframework.transaction.event.TransactionalEventListener

@DomainService
class AdminEventHandler(
    private val alarmAdapter: AlarmAdapter
) {
    @Async
    @TransactionalEventListener(phase = TransactionPhase.AFTER_COMMIT)
    fun sendReportAlarm(event: ReportEvent) {
        alarmAdapter.sendAlarm(AlarmType.REPORT, event.toSpecificMap())
    }
}