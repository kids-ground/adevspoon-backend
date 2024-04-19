package com.adevspoon.domain.techQuestion.service

import com.adevspoon.domain.common.annotation.DomainService
import com.adevspoon.domain.member.exception.MemberNotFoundException
import com.adevspoon.domain.member.repository.UserRepository
import com.adevspoon.domain.techQuestion.domain.QuestionOpenEntity
import com.adevspoon.domain.techQuestion.dto.response.QuestionInfo
import com.adevspoon.domain.techQuestion.exception.QuestionCategoryNotFoundException
import com.adevspoon.domain.techQuestion.exception.QuestionExhaustedException
import com.adevspoon.domain.techQuestion.exception.QuestionNotFoundException
import com.adevspoon.domain.techQuestion.repository.QuestionCategoryRepository
import com.adevspoon.domain.techQuestion.repository.QuestionOpenRepository
import com.adevspoon.domain.techQuestion.repository.QuestionRepository
import com.adevspoon.domain.techQuestion.repository.UserCustomizedQuestionCategoryRepository
import org.slf4j.LoggerFactory
import org.springframework.data.repository.findByIdOrNull
import org.springframework.transaction.annotation.Propagation
import org.springframework.transaction.annotation.Transactional
import java.time.LocalDate

@DomainService
class QuestionOpenDomainService(
    private val questionCategoryRepository: QuestionCategoryRepository,
    private val questionOpenRepository: QuestionOpenRepository,
    private val questionRepository: QuestionRepository,
    private val userRepository: UserRepository,
    private val userCustomizedQuestionCategoryRepository: UserCustomizedQuestionCategoryRepository,
) {
    private val logger = LoggerFactory.getLogger(this.javaClass)!!

    @Transactional(propagation = Propagation.MANDATORY)
    fun issueQuestion(memberId: Long, today: LocalDate): QuestionInfo {
        // 정책 - 1일 1회 Random 발급
        logger.info("질문발급 : memberId($memberId), today($today)")
        val user = userRepository.findByIdOrNull(memberId) ?: throw MemberNotFoundException()
        val selectedCategoryIds = userCustomizedQuestionCategoryRepository.findAllSelectedCategoryIds(user)
            .takeIf { it.isNotEmpty() }
            ?: questionCategoryRepository.findAllIds()

        val alreadyIssuedQuestionIds = questionOpenRepository.findAllIssuedQuestionIds(user)
        val candidateIssuableQuestionIds =
            (questionRepository.findAllQuestionIds(selectedCategoryIds) - alreadyIssuedQuestionIds)
                .takeIf { it.isNotEmpty() }
                ?: throw QuestionExhaustedException()

        val issuedQuestionId = candidateIssuableQuestionIds.random()
        val question = questionRepository.findByIdOrNull(issuedQuestionId) ?: throw QuestionNotFoundException()
        val issuedQuestion = QuestionOpenEntity(user = user, question = question, openDate = today.atStartOfDay())
        questionOpenRepository.save(issuedQuestion)

        user.apply { questionCnt += 1 }

        return makeQuestionInfo(issuedQuestion, candidateIssuableQuestionIds.size == 1)
    }

    private fun makeQuestionInfo(questionOpen: QuestionOpenEntity, isLast: Boolean = false): QuestionInfo {
        val category = questionCategoryRepository.findByIdOrNull(questionOpen.question.categoryId)
            ?: throw QuestionCategoryNotFoundException()
        return QuestionInfo.from(questionOpen, category.category, isLast)
    }
}