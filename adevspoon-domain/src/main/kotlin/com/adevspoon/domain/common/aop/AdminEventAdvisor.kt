package com.adevspoon.domain.common.aop

import com.adevspoon.domain.common.annotation.AdminEventType
import com.adevspoon.domain.common.annotation.AdminEvent
import com.adevspoon.domain.common.event.ReportEvent
import com.adevspoon.domain.common.exception.ReportEventInvalidReturnException
import org.aspectj.lang.JoinPoint
import org.aspectj.lang.annotation.AfterReturning
import org.aspectj.lang.annotation.Aspect
import org.aspectj.lang.reflect.MethodSignature
import org.springframework.context.ApplicationEventPublisher
import org.springframework.core.Ordered
import org.springframework.core.annotation.Order
import org.springframework.stereotype.Component


@Aspect
@Component
@Order(Ordered.LOWEST_PRECEDENCE)
class AdminEventAdvisor (
    private val eventPublisher: ApplicationEventPublisher
){
    @AfterReturning(pointcut = "@annotation(com.adevspoon.domain.common.annotation.AdminEvent)", returning = "result")
    fun afterReturningAdvice(joinPoint: JoinPoint, result: Any?) {
        val notificationAnnotation = getAnnotation(joinPoint)
        when (notificationAnnotation.type) {
            AdminEventType.REPORT -> bugReportEventPublish(result)
        }
    }

    private fun getAnnotation(joinPoint: JoinPoint) =
        (joinPoint.signature as MethodSignature).method
            .getAnnotation(AdminEvent::class.java)

    private fun bugReportEventPublish(result: Any?) {
        (result as? ReportEvent)
            ?.let {
                eventPublisher.publishEvent(it)
            } ?: throw ReportEventInvalidReturnException()
    }
}