package com.adevspoon.domain.techQuestion.service

import com.adevspoon.domain.common.annotation.DomainService
import com.adevspoon.domain.member.domain.UserEntity
import com.adevspoon.domain.member.exception.MemberNotFoundException
import com.adevspoon.domain.member.repository.UserRepository
import com.adevspoon.domain.member.service.MemberDomainService
import com.adevspoon.domain.techQuestion.domain.AnswerEntity
import com.adevspoon.domain.techQuestion.domain.QuestionEntity
import com.adevspoon.domain.techQuestion.domain.QuestionOpenEntity
import com.adevspoon.domain.techQuestion.dto.request.CreateQuestionAnswer
import com.adevspoon.domain.techQuestion.dto.response.QuestionAnswerInfo
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
) {
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