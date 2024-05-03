package com.adevspoon.domain.techQuestion.service

import com.adevspoon.domain.common.annotation.DomainService
import com.adevspoon.domain.member.domain.UserEntity
import com.adevspoon.domain.member.exception.MemberNotFoundException
import com.adevspoon.domain.member.repository.UserRepository
import com.adevspoon.domain.techQuestion.domain.QuestionCategoryEntity
import com.adevspoon.domain.techQuestion.domain.QuestionEntity
import com.adevspoon.domain.techQuestion.domain.QuestionOpenEntity
import com.adevspoon.domain.techQuestion.dto.IssuedQuestionFilter
import com.adevspoon.domain.techQuestion.dto.request.GetIssuedQuestionList
import com.adevspoon.domain.techQuestion.dto.response.IssuedQuestionListInfo
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

    @Transactional
    fun getIssuedQuestionList(request: GetIssuedQuestionList): IssuedQuestionListInfo {
        val selectedCategoryList = getCategoryList(request.categoryNameList)
        val targetCategoryIds = if (request.categoryNameList.isEmpty())
            emptyList()
        else
            selectedCategoryList.map { it.id }

        val issuedQuestionList = questionOpenRepository.findQuestionOpenList(
            IssuedQuestionFilter(
                request.memberId,
                targetCategoryIds,
                request.isAnswered
            ),
            request.sort,
            request.offset,
            request.limit
        )

        return IssuedQuestionListInfo(
            issuedQuestionList.content.map { makeQuestionInfo(it, selectedCategoryList) },
            issuedQuestionList.hasNext()
        )
    }

    @Transactional(propagation = Propagation.MANDATORY)
    fun issueQuestion(memberId: Long, today: LocalDate): QuestionInfo {
        // 정책 - 1일 1회 Random 발급
        logger.info("질문발급 : memberId($memberId), today($today)")
        val user = getMember(memberId)
        val selectedCategoryIds = userCustomizedQuestionCategoryRepository.findAllSelectedCategoryIds(user)
            .takeIf { it.isNotEmpty() }
            ?: questionCategoryRepository.findAllIds()

        val alreadyIssuedQuestionIds = questionOpenRepository.findAllIssuedQuestionIds(user)
        val candidateIssuableQuestionIds =
            (questionRepository.findAllQuestionIds(selectedCategoryIds) - alreadyIssuedQuestionIds)
                .takeIf { it.isNotEmpty() }
                ?: throw QuestionExhaustedException()

        val issuedQuestionId = candidateIssuableQuestionIds.random()
        val question = getQuestion(issuedQuestionId)
        val issuedQuestion = QuestionOpenEntity(user = user, question = question, openDate = today.atStartOfDay())

        questionOpenRepository.save(issuedQuestion)
        user.increaseQuestionCnt()

        return makeQuestionInfo(issuedQuestion, candidateIssuableQuestionIds.size == 1)
    }

    private fun getCategoryList(categoryNameList: List<String>): List<QuestionCategoryEntity> {
        return if (categoryNameList.isNotEmpty())
            questionCategoryRepository.findByNames(categoryNameList)
        else
            questionCategoryRepository.findAll()
    }

    private fun getQuestion(questionId: Long): QuestionEntity {
        return questionRepository.findByIdOrNull(questionId) ?: throw QuestionNotFoundException()
    }

    private fun getMember(memberId: Long): UserEntity {
        return userRepository.findByIdOrNull(memberId) ?: throw MemberNotFoundException()
    }

    private fun makeQuestionInfo(questionOpen: QuestionOpenEntity, categoryList: List<QuestionCategoryEntity>): QuestionInfo {
        val category = categoryList.find { it.id == questionOpen.question.categoryId }
            ?: throw QuestionCategoryNotFoundException()
        return QuestionInfo.from(questionOpen, category.category)
    }

    private fun makeQuestionInfo(questionOpen: QuestionOpenEntity, isLast: Boolean = false): QuestionInfo {
        val category = questionCategoryRepository.findByIdOrNull(questionOpen.question.categoryId)
            ?: throw QuestionCategoryNotFoundException()
        return QuestionInfo.from(questionOpen, category.category, isLast)
    }
}