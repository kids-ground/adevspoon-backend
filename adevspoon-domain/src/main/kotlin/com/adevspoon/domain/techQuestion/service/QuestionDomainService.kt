package com.adevspoon.domain.techQuestion.service

import com.adevspoon.domain.common.annotation.DistributedLock
import com.adevspoon.domain.common.annotation.DomainService
import com.adevspoon.domain.common.utils.isToday
import com.adevspoon.domain.member.domain.UserEntity
import com.adevspoon.domain.member.exception.MemberNotFoundException
import com.adevspoon.domain.member.repository.UserRepository
import com.adevspoon.domain.techQuestion.domain.QuestionEntity
import com.adevspoon.domain.techQuestion.domain.QuestionOpenEntity
import com.adevspoon.domain.techQuestion.domain.UserCustomizedQuestionCategoryEntity
import com.adevspoon.domain.techQuestion.domain.UserCustomizedQuestionCategoryId
import com.adevspoon.domain.techQuestion.dto.request.GetTodayQuestion
import com.adevspoon.domain.techQuestion.dto.response.*
import com.adevspoon.domain.techQuestion.exception.QuestionCategoryNotFoundException
import com.adevspoon.domain.techQuestion.exception.QuestionNotFoundException
import com.adevspoon.domain.techQuestion.exception.QuestionNotOpenedException
import com.adevspoon.domain.techQuestion.repository.QuestionCategoryRepository
import com.adevspoon.domain.techQuestion.repository.QuestionOpenRepository
import com.adevspoon.domain.techQuestion.repository.QuestionRepository
import com.adevspoon.domain.techQuestion.repository.UserCustomizedQuestionCategoryRepository
import org.springframework.data.repository.findByIdOrNull
import org.springframework.transaction.annotation.Transactional

@DomainService
class QuestionDomainService(
    private val questionCategoryRepository: QuestionCategoryRepository,
    private val questionRepository: QuestionRepository,
    private val questionOpenRepository: QuestionOpenRepository,
    private val userRepository: UserRepository,
    private val userCustomizedQuestionCategoryRepository: UserCustomizedQuestionCategoryRepository,
    private val questionOpenDomainService: QuestionOpenDomainService,
) {
    @Transactional(readOnly = true)
    fun getQuestion(memberId: Long, questionId: Long): QuestionInfo {
        val user = getMember(memberId)
        val question = getQuestion(questionId)
        val issuedQuestion = getIssuedQuestion(question, user)

        return makeQuestionInfo(issuedQuestion)
    }

    @Transactional
    @DistributedLock(keyClass = [GetTodayQuestion::class])
    fun getOrCreateTodayQuestion(request: GetTodayQuestion): QuestionInfo {
        val user = getMember(request.memberId)
        val latestIssuedQuestion = questionOpenRepository.findLatestWithQuestionAndAnswer(user)
        val isTodayQuestion = latestIssuedQuestion?.openDate?.isToday() ?: false

        return if(isTodayQuestion) makeQuestionInfo(latestIssuedQuestion!!)
        else questionOpenDomainService.issueQuestion(request.memberId, request.today)
    }

    @Transactional
    fun getQuestionCategories(memberId: Long): List<QuestionCategoryInfo> {
        val user = getMember(memberId)

        val allCategories = questionCategoryRepository.findAll()
        val selectedCategories = userCustomizedQuestionCategoryRepository.findAllSelectedCategory(user)
            .takeIf { it.isNotEmpty() }
            ?: allCategories

        val categoryQuestionCount = questionRepository.findQuestionCountGroupByCategory()
        val categoryIssuedQuestionCount = questionOpenRepository.findIssuedQuestionGroupByCategory(user)
        val categoryRemainQuestionCount = categoryQuestionCount.subtractCount(categoryIssuedQuestionCount)

        return allCategories.map { category ->
            val isDepleted = categoryRemainQuestionCount.find{ it.categoryId == category.id }?.isDepleted ?: true
            val isSelected = selectedCategories.contains(category)
            QuestionCategoryInfo.from(category, isDepleted, isSelected)
        }
    }

    @Transactional
    fun updateQuestionCategory(user: UserEntity, categoryIds: List<Long>) {
        val newCustomizedQuestionCategories = runCatching {
            questionCategoryRepository.findQuestionCategoryByIds(categoryIds)
        }
            .onFailure { throw QuestionCategoryNotFoundException() }
            .getOrNull()
            ?.map { UserCustomizedQuestionCategoryEntity(UserCustomizedQuestionCategoryId(it, user)) }
            ?: throw QuestionCategoryNotFoundException()

        userCustomizedQuestionCategoryRepository.deleteAllByUser(user)
        // FIXME: saveAll 한 번에 처리 필요(select N번, insert N번 나감)
        userCustomizedQuestionCategoryRepository.saveAll(newCustomizedQuestionCategories)
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

    private fun makeQuestionInfo(questionOpen: QuestionOpenEntity): QuestionInfo {
        val category = questionCategoryRepository.findByIdOrNull(questionOpen.question.categoryId)
            ?: throw QuestionCategoryNotFoundException()
        return QuestionInfo.from(questionOpen, category.category)
    }
}