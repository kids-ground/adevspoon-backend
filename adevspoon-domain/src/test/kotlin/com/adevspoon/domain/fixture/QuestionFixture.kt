package com.adevspoon.domain.fixture

import com.adevspoon.domain.member.domain.UserEntity
import com.adevspoon.domain.techQuestion.domain.QuestionCategoryEntity
import com.adevspoon.domain.techQuestion.domain.QuestionEntity
import com.adevspoon.domain.techQuestion.domain.QuestionOpenEntity
import com.adevspoon.domain.techQuestion.domain.enums.QuestionCategoryTopic
import com.adevspoon.domain.techQuestion.dto.response.CategoryQuestionCountDto
import com.adevspoon.domain.techQuestion.dto.response.QuestionCategoryInfo
import com.adevspoon.domain.techQuestion.dto.response.QuestionInfo
import java.time.LocalDate
import java.time.LocalDateTime

class QuestionFixture {
    companion object {
        fun createQuestion(
            id: Long? = null,
            question: String = "question",
            difficulty: Int = 0,
            studyLink: String = "studyLink",
            notionId: String = "notionId",
            categoryId: Long = 1
        ) = QuestionEntity(
            id = id ?: 1,
            question = question,
            difficulty = difficulty,
            studyLink = studyLink,
            notionId = notionId,
            categoryId = categoryId,
        )

        fun createQuestionOpen(
            id: Long? = null,
            question: QuestionEntity = createQuestion(),
            openDate: LocalDateTime = LocalDateTime.now(),
            user: UserEntity = MemberFixture.createMember()
        ) = QuestionOpenEntity(
            id = id ?: 1,
            openDate = openDate,
            question = question,
            user = user,
        )

        fun createQuestionCategory(
            id: Long = 1,
            category: String = "category",
            topic: QuestionCategoryTopic = QuestionCategoryTopic.CS,
            subtitle: String = "subtitle",
            description: String = "description",
            iconUrl: String = "iconUrl",
            backgroundColor: String = "colors",
            accentColor: String = "colors",
            iconColor: String = "colors",
        ) = QuestionCategoryEntity(
            id = id,
            category = category,
            topic = topic,
            subtitle = subtitle,
            description = description,
            iconUrl = iconUrl,
            backgroundColor = backgroundColor,
            accentColor = accentColor,
            iconColor = iconColor,
        )

        fun createQuestionInfo(
            questionId: Long = 1,
            question: String = "question",
            difficulty: Int = 0,
            studyLink: String = "studyLink",
            categoryName: String = "category",
            tag: List<String> = listOf(),
            openDate: LocalDate = LocalDate.now(),
            answerId: Long? = null,
            isLast: Boolean = false,
            createdAt: LocalDateTime = LocalDateTime.now(),
            updatedAt: LocalDateTime = LocalDateTime.now(),
        ) = QuestionInfo(
            questionId = questionId,
            question = question,
            difficulty = difficulty,
            studyLink = studyLink,
            categoryName = categoryName,
            tag = tag,
            openDate = openDate,
            answerId = answerId,
            isLast = isLast,
            createdAt = createdAt,
            updatedAt = updatedAt,
        )

        fun createQuestionCount(
            categoryId: Long = 1,
            questionCount: Long = 1,
        ) = CategoryQuestionCountDto(
            categoryId, questionCount
        )

        fun createQuestionCategoryInfo(
            id: Long = 1,
            depleted: Boolean = false,
            selected: Boolean = false,
            name: String = "name",
        ) = QuestionCategoryInfo(
            id, name, depleted, selected
        )
    }
}