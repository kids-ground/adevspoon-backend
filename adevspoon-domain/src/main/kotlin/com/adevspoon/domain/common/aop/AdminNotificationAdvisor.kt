package com.adevspoon.domain.common.aop

import com.adevspoon.domain.common.annotation.AdminMessageType
import com.adevspoon.domain.common.annotation.AdminNotificationEvent
import com.adevspoon.domain.common.event.ReportEvent
import com.adevspoon.domain.common.exception.ReportEventInvalidReturnException
import org.aspectj.lang.JoinPoint
import org.aspectj.lang.annotation.AfterReturning
import org.aspectj.lang.annotation.Aspect
import org.aspectj.lang.reflect.MethodSignature
import org.springframework.context.ApplicationEventPublisher
import org.springframework.stereotype.Component


@Aspect
@Component
class AdminNotificationAdvisor (
    private val eventPublisher: ApplicationEventPublisher
){

    @AfterReturning(pointcut = "@annotation(com.adevspoon.domain.common.annotation.AdminNotificationEvent)", returning = "result")
    fun afterReturningAdvice(joinPoint: JoinPoint, result: Any?) {
        val notificationAnnotation = getAnnotation(joinPoint)
        when (notificationAnnotation.type) {
            AdminMessageType.REPORT -> bugReportEventPublish(result)
        }
    }

    private fun getAnnotation(joinPoint: JoinPoint) =
        (joinPoint.signature as MethodSignature).method
            .getAnnotation(AdminNotificationEvent::class.java)

    private fun bugReportEventPublish(result: Any?) {
        (result as? ReportEvent)
            ?.let {
                eventPublisher.publishEvent(it)
            } ?: throw ReportEventInvalidReturnException()
    }
}