package com.adevspoon.domain.techQuestion.service

import com.adevspoon.domain.common.annotation.DomainService
import com.adevspoon.domain.member.domain.UserEntity
import com.adevspoon.domain.techQuestion.domain.UserCustomizedQuestionCategoryEntity
import com.adevspoon.domain.techQuestion.domain.UserCustomizedQuestionCategoryId
import com.adevspoon.domain.techQuestion.exception.QuestionCategoryNotFoundException
import com.adevspoon.domain.techQuestion.exception.QuestionDomainErrorCode
import com.adevspoon.domain.techQuestion.repository.QuestionCategoryRepository
import com.adevspoon.domain.techQuestion.repository.UserCustomizedQuestionCategoryRepository
import org.springframework.transaction.annotation.Transactional

@DomainService
class QuestionDomainService(
    private val questionCategoryRepository: QuestionCategoryRepository,
    private val userCustomizedQuestionCategoryRepository: UserCustomizedQuestionCategoryRepository
) {

    @Transactional
    fun updateQuestionCategory(user: UserEntity, categoryIds: List<Long>) {
        val newCustomizedQuestionCategories = runCatching {
            questionCategoryRepository.findQuestionCategoryByIds(categoryIds)
        }
            .onFailure { throw QuestionCategoryNotFoundException() }
            .getOrNull()
            ?.map { UserCustomizedQuestionCategoryEntity(UserCustomizedQuestionCategoryId(it, user)) }
            ?: throw QuestionCategoryNotFoundException()

        userCustomizedQuestionCategoryRepository.deleteAllByUserId(user.id)
        // TODO: saveAll 한 번에 처리 필요(select N번, insert N번 나감)
        userCustomizedQuestionCategoryRepository.saveAll(newCustomizedQuestionCategories)
    }
}