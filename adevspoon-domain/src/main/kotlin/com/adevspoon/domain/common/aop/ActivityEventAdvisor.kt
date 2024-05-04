package com.adevspoon.domain.common.aop

import com.adevspoon.domain.board.dto.response.BoardPost
import com.adevspoon.domain.board.exception.BoardPostInvalidReturnException
import com.adevspoon.domain.common.annotation.ActivityEvent
import com.adevspoon.domain.common.annotation.ActivityEventType
import com.adevspoon.domain.common.event.AnswerActivityEvent
import com.adevspoon.domain.common.event.AttendanceActivityEvent
import com.adevspoon.domain.common.event.BoardPostActivityEvent
import com.adevspoon.domain.member.dto.response.MemberProfile
import com.adevspoon.domain.member.exception.MemberAttendanceInvalidReturnException
import com.adevspoon.domain.techQuestion.dto.response.QuestionAnswerInfo
import com.adevspoon.domain.techQuestion.exception.QuestionAnswerInvalidReturnException
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
class ActivityEventAdvisor(
    private val eventPublisher: ApplicationEventPublisher
) {
    @AfterReturning(pointcut = "@annotation(com.adevspoon.domain.common.annotation.ActivityEvent)", returning = "result")
    fun afterReturningAdvice(joinPoint: JoinPoint, result: Any?) {
        val activityAnnotation = getAnnotation(joinPoint)

        when(activityAnnotation.type) {
            ActivityEventType.ATTENDANCE -> attendanceEventPublish(result)
            ActivityEventType.ANSWER -> answerEventPublish(result)
            ActivityEventType.BOARD_POST -> boardPostEventPublish(result)
        }
    }

    private fun getAnnotation(joinPoint: JoinPoint) =
        (joinPoint.signature as MethodSignature).method
            .getAnnotation(ActivityEvent::class.java)

    private fun boardPostEventPublish(result: Any?) {
        (result as? BoardPost)
            ?.let {
                eventPublisher.publishEvent(BoardPostActivityEvent(it.user.memberId))
            } ?: throw BoardPostInvalidReturnException()
    }

    private fun attendanceEventPublish(result: Any?) {
        (result as? MemberProfile)
            ?.let {
                eventPublisher.publishEvent(AttendanceActivityEvent(it.memberId))
            } ?: throw MemberAttendanceInvalidReturnException()
    }

    private fun answerEventPublish(result: Any?) {
        (result as? QuestionAnswerInfo)
            ?.let {
                eventPublisher.publishEvent(AnswerActivityEvent(it.user.memberId, it.answerId))
            } ?: throw QuestionAnswerInvalidReturnException()
    }
}