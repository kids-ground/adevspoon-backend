package com.adevspoon.domain.techQuestion.service

import com.adevspoon.domain.common.annotation.*
import com.adevspoon.domain.common.entity.ReportEntity
import com.adevspoon.domain.common.event.ReportEvent
import com.adevspoon.domain.common.repository.LikeRepository
import com.adevspoon.domain.common.repository.ReportRepository
import com.adevspoon.domain.common.service.LikeDomainService
import com.adevspoon.domain.common.utils.isToday
import com.adevspoon.domain.member.domain.UserEntity
import com.adevspoon.domain.member.dto.response.MemberProfile
import com.adevspoon.domain.member.exception.MemberNotFoundException
import com.adevspoon.domain.member.repository.BadgeRepository
import com.adevspoon.domain.member.repository.UserRepository
import com.adevspoon.domain.member.service.MemberDomainService
import com.adevspoon.domain.techQuestion.domain.AnswerEntity
import com.adevspoon.domain.techQuestion.domain.QuestionEntity
import com.adevspoon.domain.techQuestion.domain.QuestionOpenEntity
import com.adevspoon.domain.techQuestion.dto.request.CreateQuestionAnswer
import com.adevspoon.domain.techQuestion.dto.request.GetQuestionAnswerList
import com.adevspoon.domain.techQuestion.dto.request.ModifyQuestionAnswer
import com.adevspoon.domain.techQuestion.dto.response.QuestionAnswerInfo
import com.adevspoon.domain.techQuestion.dto.response.QuestionAnswerListInfo
import com.adevspoon.domain.techQuestion.exception.*
import com.adevspoon.domain.techQuestion.repository.AnswerRepository
import com.adevspoon.domain.techQuestion.repository.QuestionOpenRepository
import com.adevspoon.domain.techQuestion.repository.QuestionRepository
import org.springframework.data.repository.findByIdOrNull
import org.springframework.transaction.annotation.Transactional
import java.time.LocalDate

@DomainService
class AnswerDomainService(
    private val questionRepository: QuestionRepository,
    private val questionOpenRepository: QuestionOpenRepository,
    private val answerRepository: AnswerRepository,
    private val userRepository: UserRepository,
    private val badgeRepository: BadgeRepository,
    private val reportRepository: ReportRepository,
    private val likeRepository: LikeRepository,
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
        issuedQuestion.addAnswer(answer)
        requestMember.increaseAnswerCnt()

        return QuestionAnswerInfo.from(
            answer,
            memberDomainService.getMemberProfile(request.requestMemberId),
            issuedQuestion.openDate.toLocalDate(),
            false
        )
    }

    @Transactional(readOnly = true)
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

    @Transactional(readOnly = true)
    fun getTodayBestAnswerList(memberId: Long): QuestionAnswerListInfo {
        val member = getMember(memberId)
        val latestIssuedQuestion = getLatestIssuedQuestion(member)

        if (!latestIssuedQuestion.openDate.isToday()) throw QuestionNotIssuedException()

        val answerList = answerRepository.findBestAnswerListByQuestionId(latestIssuedQuestion.question.id)
            .takeIf { it.isNotEmpty() }
            ?: throw QuestionAnswerNotFoundException()

        return makeQuestionAnswerListInfo(memberId, answerList, false)
    }

    @Transactional(readOnly = true)
    fun getAnswerList(request: GetQuestionAnswerList): QuestionAnswerListInfo {
        if (request.questionId == null) throw QuestionNotFoundException()

        val answerList = answerRepository.findQuestionAnswerList(
            request.questionId,
            request.sort,
            request.offset,
            request.limit
        )

        return makeQuestionAnswerListInfo(request.memberId, answerList.content, answerList.hasNext())
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
    @AdminEvent(type = AdminEventType.REPORT)
    fun reportAnswer(answerId: Long, memberId: Long) : ReportEvent{
        val member = getMember(memberId)
        val answer = getAnswerWithUserAndQuestion(answerId)
            .takeIf { it.user.id != memberId }
            ?: throw QuestionAnswerReportNotAllowedException()
        checkReport(member, answer)

        val report = reportRepository.save(ReportEntity(user = member, postType = "answer", post = answer))

        return ReportEvent(content = answer.answer ?: "",report = report)
    }

    private fun makeQuestionAnswerListInfo(
        requestMemberId: Long,
        answerList: List<AnswerEntity>,
        hasNext: Boolean
    ): QuestionAnswerListInfo {
        val badgeList = badgeRepository.findAll()
        val likedAnswerIdsList = likeRepository.findAllIdsByUserIdAndAnswerIds(requestMemberId, answerList.map { it.id })

        return QuestionAnswerListInfo(
            answerList.map {
                QuestionAnswerInfo.from(
                    it,
                    MemberProfile.from(
                        it.user,
                        null,
                        badgeList.find { badge -> badge.id == it.user.representativeBadge }),
                    LocalDate.now(), // 무의미한 값
                    likedAnswerIdsList.contains(it.id)
                )
            },
            hasNext
        )
    }

    private fun checkReport(member: UserEntity, answer: AnswerEntity) {
        reportRepository.findAllByUserAndPost(member, answer)
            .takeIf { it.isEmpty() }
            ?: throw QuestionAnswerReportAlreadyExistException()
    }

    private fun getLatestIssuedQuestion(member: UserEntity): QuestionOpenEntity {
        return questionOpenRepository.findLatestWithQuestionAndAnswer(member) ?: throw QuestionNotOpenedException()
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