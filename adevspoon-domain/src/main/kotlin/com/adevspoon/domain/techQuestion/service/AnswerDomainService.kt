package com.adevspoon.domain.techQuestion.service

import com.adevspoon.domain.common.annotation.ActivityEvent
import com.adevspoon.domain.common.annotation.ActivityEventType
import com.adevspoon.domain.common.annotation.DomainService
import com.adevspoon.domain.common.service.LikeDomainService
import com.adevspoon.domain.member.domain.UserEntity
import com.adevspoon.domain.member.exception.MemberNotFoundException
import com.adevspoon.domain.member.repository.UserRepository
import com.adevspoon.domain.member.service.MemberDomainService
import com.adevspoon.domain.techQuestion.domain.AnswerEntity
import com.adevspoon.domain.techQuestion.domain.QuestionEntity
import com.adevspoon.domain.techQuestion.domain.QuestionOpenEntity
import com.adevspoon.domain.techQuestion.dto.request.CreateQuestionAnswer
import com.adevspoon.domain.techQuestion.dto.request.ModifyQuestionAnswer
import com.adevspoon.domain.techQuestion.dto.response.QuestionAnswerInfo
import com.adevspoon.domain.techQuestion.exception.QuestionAnswerEditUnauthorizedException
import com.adevspoon.domain.techQuestion.exception.QuestionAnswerNotFoundException
import com.adevspoon.domain.techQuestion.exception.QuestionNotFoundException
import com.adevspoon.domain.techQuestion.exception.QuestionNotOpenedException
import com.adevspoon.domain.techQuestion.repository.AnswerRepository
import com.adevspoon.domain.techQuestion.repository.QuestionOpenRepository
import com.adevspoon.domain.techQuestion.repository.QuestionRepository
import jakarta.transaction.Transactional
import org.springframework.data.repository.findByIdOrNull

@DomainService
class AnswerDomainService(
    private val questionRepository: QuestionRepository,
    private val questionOpenRepository: QuestionOpenRepository,
    private val answerRepository: AnswerRepository,
    private val userRepository: UserRepository,
    private val memberDomainService: MemberDomainService,
    private val likeDomainService: LikeDomainService
) {
    @ActivityEvent(type = ActivityEventType.ANSWER)
    @Transactional
    fun registerQuestionAnswer(request: CreateQuestionAnswer): QuestionAnswerInfo {
        val requestMember = getMember(request.requestMemberId)
        val question = getQuestion(request.questionId)
        val issuedQuestion = getIssuedQuestion(question, requestMember)
        val answer = AnswerEntity(question = question, answer = request.answer, user = requestMember)

        answerRepository.save(answer)
        requestMember.apply { answerCnt += 1 }

        return QuestionAnswerInfo.from(
            answer,
            memberDomainService.getMemberProfile(request.requestMemberId),
            issuedQuestion.openDate.toLocalDate(),
            false
        )
    }

    @Transactional
    fun getAnswerDetail(answerId: Long, requestMemberId: Long): QuestionAnswerInfo {
        val requestMember = getMember(requestMemberId)
        val answer = getAnswer(answerId)
        val issuedQuestion = getIssuedQuestion(answer.question, requestMember)
        val isLiked = likeDomainService.isUserLikedAnswer(requestMember, answer)

        return QuestionAnswerInfo.from(
            answer,
            memberDomainService.getOtherMemberProfile(answer.user.id),
            issuedQuestion.openDate.toLocalDate(),
            isLiked
        )
    }

    @Transactional
    fun modifyAnswerInfo(request: ModifyQuestionAnswer): QuestionAnswerInfo {
        getAnswer(request.answerId)
            .takeIf { it.user.id == request.memberId }
            .also { it?.answer = request.answer }
            ?: throw QuestionAnswerEditUnauthorizedException()

        return getAnswerDetail(request.answerId, request.memberId)
    }

    private fun getAnswer(answerId: Long): AnswerEntity {
        return answerRepository.findWithQuestionAndUser(answerId) ?: throw QuestionAnswerNotFoundException()
    }

    private fun getQuestion(questionId: Long): QuestionEntity {
        return questionRepository.findByIdOrNull(questionId) ?: throw QuestionNotFoundException()
    }

    private fun getMember(memberId: Long): UserEntity {
        return userRepository.findByIdOrNull(memberId) ?: throw MemberNotFoundException()
    }

    private fun getIssuedQuestion(question: QuestionEntity, member: UserEntity): QuestionOpenEntity {
        return questionOpenRepository.findByQuestionAndUser(question, member) ?: throw QuestionNotOpenedException()
    }
}