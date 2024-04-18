package com.adevspoon.domain.fixture

import com.adevspoon.domain.member.domain.UserEntity
import com.adevspoon.domain.techQuestion.domain.QuestionCategoryEntity
import com.adevspoon.domain.techQuestion.domain.QuestionEntity
import com.adevspoon.domain.techQuestion.domain.QuestionOpenEntity
import com.adevspoon.domain.techQuestion.domain.enums.QuestionCategoryTopic
import java.time.LocalDateTime

class QuestionFixture {
    companion object {
        fun createQuestion(
            id: Long? = null,
            question: String = "question",
            difficulty: Int = 0,
            studyLink: String = "studyLink",
            notionId: String = "notionId",
            categoryId: Long = 0
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
            id: Long? = null,
            category: String = "category",
            topic: QuestionCategoryTopic = QuestionCategoryTopic.CS,
            subtitle: String = "subtitle",
            description: String = "description",
            iconUrl: String = "iconUrl",
            backgroundColor: String = "backgroundColor",
            accentColor: String = "accentColor",
            iconColor: String = "iconColor",
        ) = QuestionCategoryEntity(
            id = id ?: 1,
            category = category,
            topic = topic,
            subtitle = subtitle,
            description = description,
            iconUrl = iconUrl,
            backgroundColor = backgroundColor,
            accentColor = accentColor,
            iconColor = iconColor,
        )
    }
}