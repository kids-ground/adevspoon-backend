package com.adevspoon.domain.techQuestion.service

import com.adevspoon.domain.common.annotation.ActivityEvent
import com.adevspoon.domain.common.annotation.ActivityEventType
import com.adevspoon.domain.common.annotation.DomainService
import com.adevspoon.domain.common.entity.ReportEntity
import com.adevspoon.domain.common.repository.ReportRepository
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
import com.adevspoon.domain.techQuestion.exception.*
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
    private val reportRepository: ReportRepository,
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
        issuedQuestion.setAnswer(answer)
        requestMember.increaseAnswerCnt()

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
        val answer = getAnswerWithUserAndQuestion(answerId)
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
        getAnswerWithUserAndQuestion(request.answerId)
            .takeIf { it.user.id == request.memberId }
            .also { it?.answer = request.answer }
            ?: throw QuestionAnswerEditUnauthorizedException()

        return getAnswerDetail(request.answerId, request.memberId)
    }

    @Transactional
    fun toggleAnswerLike(answerId: Long, memberId: Long, isLiked: Boolean) {
        likeDomainService.toggleAnswerLike(
            getAnswer(answerId),
            getMember(memberId),
            isLiked
        )
    }

    @Transactional
    fun reportAnswer(answerId: Long, memberId: Long) {
        val member = getMember(memberId)
        val answer = getAnswerWithUserAndQuestion(answerId)
            .takeIf { it.user.id != memberId }
            ?: throw QuestionAnswerReportNotAllowedException()
        checkReport(member, answer)

        reportRepository.save(ReportEntity(user = member, postType = "answer", post = answer))
    }

    private fun checkReport(member: UserEntity, answer: AnswerEntity) {
        reportRepository.findAllByUserAndPost(member, answer)
            .takeIf { it.isEmpty() }
            ?: throw QuestionAnswerReportAlreadyExistException()
    }

    private fun getAnswerWithUserAndQuestion(answerId: Long): AnswerEntity {
        return answerRepository.findWithQuestionAndUser(answerId) ?: throw QuestionAnswerNotFoundException()
    }

    private fun getAnswer(answerId: Long): AnswerEntity {
        return answerRepository.findByIdOrNull(answerId) ?: throw QuestionAnswerNotFoundException()
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