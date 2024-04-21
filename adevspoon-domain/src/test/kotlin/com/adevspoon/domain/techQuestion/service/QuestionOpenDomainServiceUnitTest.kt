package com.adevspoon.domain.techQuestion.service

import com.adevspoon.domain.fixture.MemberFixture
import com.adevspoon.domain.fixture.QuestionFixture
import com.adevspoon.domain.member.domain.UserEntity
import com.adevspoon.domain.member.repository.UserRepository
import com.adevspoon.domain.techQuestion.domain.QuestionCategoryEntity
import com.adevspoon.domain.techQuestion.domain.QuestionEntity
import com.adevspoon.domain.techQuestion.domain.QuestionOpenEntity
import com.adevspoon.domain.techQuestion.exception.QuestionExhaustedException
import com.adevspoon.domain.techQuestion.repository.QuestionCategoryRepository
import com.adevspoon.domain.techQuestion.repository.QuestionOpenRepository
import com.adevspoon.domain.techQuestion.repository.QuestionRepository
import com.adevspoon.domain.techQuestion.repository.UserCustomizedQuestionCategoryRepository
import io.mockk.every
import io.mockk.mockk
import io.mockk.verify
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Nested
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.assertThrows
import org.springframework.data.repository.findByIdOrNull
import java.time.LocalDate

class QuestionOpenDomainServiceUnitTest {
    private val questionCategoryRepository = mockk<QuestionCategoryRepository>()
    private val questionOpenRepository = mockk<QuestionOpenRepository>()
    private val questionRepository = mockk<QuestionRepository>()
    private val userRepository = mockk<UserRepository>()
    private val userCustomizedQuestionCategoryRepository = mockk<UserCustomizedQuestionCategoryRepository>()

    private lateinit var questionOpenDomainService: QuestionOpenDomainService

    @BeforeEach
    fun setUp() {
        questionOpenDomainService = QuestionOpenDomainService(
            questionCategoryRepository,
            questionOpenRepository,
            questionRepository,
            userRepository,
            userCustomizedQuestionCategoryRepository
        )
    }

    @Nested
    inner class IssueQuestion() {
        private lateinit var member: UserEntity
        private lateinit var question: QuestionEntity
        private lateinit var questionOpen: QuestionOpenEntity
        private lateinit var questionCategory: QuestionCategoryEntity

        @BeforeEach
        fun setup() {
            member = MemberFixture.createMember(1)
            question = QuestionFixture.createQuestion(1)
            questionOpen = QuestionFixture.createQuestionOpen(1, question, user = member)
            questionCategory = QuestionFixture.createQuestionCategory(1)

            every { userRepository.findByIdOrNull(any()) } returns member
            every { userCustomizedQuestionCategoryRepository.findAllSelectedCategoryIds(any()) } returns listOf(1, 2)
            every { questionCategoryRepository.findAllIds() } returns listOf(1, 2)

            every { questionRepository.findByIdOrNull(any()) } returns question
            every { questionOpenRepository.save(any()) } returns questionOpen
            every { questionCategoryRepository.findByIdOrNull(any()) } returns questionCategory
        }

        @Test
        fun `SUCCESS - 새로운 문제 발급 성공(커먼 케이스)`() {
            // given
            every { questionOpenRepository.findAllIssuedQuestionIds(any()) } returns setOf(3,4)
            every { questionRepository.findAllQuestionIds(any()) } returns setOf(1,2,3,4)

            // when
            val issueQuestion = questionOpenDomainService.issueQuestion(member.id, LocalDate.now())

            // then
            assertEquals(question.id, issueQuestion.questionId)
            assertEquals(false, issueQuestion.isLast)

            verify { userCustomizedQuestionCategoryRepository.findAllSelectedCategoryIds(any()) }
            verify(exactly = 0) { questionCategoryRepository.findAllIds() }

            verify { questionRepository.findAllQuestionIds(any()) }
            verify { questionOpenRepository.findAllIssuedQuestionIds(any()) }
        }

        @Test
        fun `SUCCESS - 새로운 문제발급 성공(마지막 문제 발급)`() {
            // given
            every { questionOpenRepository.findAllIssuedQuestionIds(any()) } returns setOf(3,4)
            every { questionRepository.findAllQuestionIds(any()) } returns setOf(1,3,4)

            // when
            val issueQuestion = questionOpenDomainService.issueQuestion(member.id, LocalDate.now())

            // then
            assertEquals(question.id, issueQuestion.questionId)
            assertEquals(true, issueQuestion.isLast)

            verify { userCustomizedQuestionCategoryRepository.findAllSelectedCategoryIds(any()) }
            verify(exactly = 0) { questionCategoryRepository.findAllIds() }

            verify { questionRepository.findAllQuestionIds(any()) }
            verify { questionOpenRepository.findAllIssuedQuestionIds(any()) }
        }

        @Test
        fun `FAIL - 문제 고갈`() {
            // given
            every { questionOpenRepository.findAllIssuedQuestionIds(any()) } returns setOf(3,4)
            every { questionRepository.findAllQuestionIds(any()) } returns setOf(3,4)

            // when, then
            assertThrows<QuestionExhaustedException> {
                questionOpenDomainService.issueQuestion(member.id, LocalDate.now())
            }

            verify { userCustomizedQuestionCategoryRepository.findAllSelectedCategoryIds(any()) }
            verify(exactly = 0) { questionCategoryRepository.findAllIds() }

            verify { questionRepository.findAllQuestionIds(any()) }
            verify { questionOpenRepository.findAllIssuedQuestionIds(any()) }

            verify(exactly = 0) { questionOpenRepository.save(any()) }
        }
    }
}