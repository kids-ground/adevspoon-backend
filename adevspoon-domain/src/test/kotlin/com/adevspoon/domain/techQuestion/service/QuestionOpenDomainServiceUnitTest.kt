package com.adevspoon.domain.techQuestion.service

import com.adevspoon.domain.annotation.UnitTest
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
import io.mockk.impl.annotations.InjectMockKs
import io.mockk.impl.annotations.MockK
import io.mockk.verify

import org.assertj.core.api.Assertions.assertThat
import org.assertj.core.api.Assertions.assertThatThrownBy
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Nested
import org.junit.jupiter.api.Test
import org.springframework.data.repository.findByIdOrNull
import java.time.LocalDate

@UnitTest
class QuestionOpenDomainServiceUnitTest {
    @MockK private lateinit var questionCategoryRepository: QuestionCategoryRepository
    @MockK private lateinit var questionRepository: QuestionRepository
    @MockK private lateinit var questionOpenRepository: QuestionOpenRepository
    @MockK private lateinit var userRepository: UserRepository
    @MockK private lateinit var userCustomizedQuestionCategoryRepository: UserCustomizedQuestionCategoryRepository

    @InjectMockKs private lateinit var questionOpenDomainService: QuestionOpenDomainService

    @Nested
    inner class IssueQuestionUnitTests() {
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
        fun `SUCCESS - 발급 가능한 문제들 중 랜덤하게 1개를 발급한다`() {
            // given
            every { questionOpenRepository.findAllIssuedQuestionIds(any()) } returns setOf(3,4)
            every { questionRepository.findAllQuestionIds(any()) } returns setOf(1,2,3,4)

            // when
            val issueQuestion = questionOpenDomainService.issueQuestion(member.id, LocalDate.now())

            // then
            assertThat(issueQuestion)
                .extracting("questionId", "isLast")
                .containsExactly(question.id, false)

            verify { userCustomizedQuestionCategoryRepository.findAllSelectedCategoryIds(any()) }
            verify(exactly = 0) { questionCategoryRepository.findAllIds() }
            verify { questionRepository.findAllQuestionIds(any()) }
            verify { questionOpenRepository.findAllIssuedQuestionIds(any()) }
        }

        @Test
        fun `SUCCESS - 발급 가능한 문제가 마지막 1개라면 리턴값에 이를 표시한다`() {
            // given
            every { questionOpenRepository.findAllIssuedQuestionIds(any()) } returns setOf(3,4)
            every { questionRepository.findAllQuestionIds(any()) } returns setOf(1,3,4)

            // when
            val issueQuestion = questionOpenDomainService.issueQuestion(member.id, LocalDate.now())

            // then
            assertThat(issueQuestion)
                .extracting("questionId", "isLast")
                .containsExactly(question.id, true)

            verify { userCustomizedQuestionCategoryRepository.findAllSelectedCategoryIds(any()) }
            verify(exactly = 0) { questionCategoryRepository.findAllIds() }
            verify { questionRepository.findAllQuestionIds(any()) }
            verify { questionOpenRepository.findAllIssuedQuestionIds(any()) }
        }

        @Test
        fun `FAIL - 발급 가능한 문제가 없다면 예외가 발생한다`() {
            // given
            every { questionOpenRepository.findAllIssuedQuestionIds(any()) } returns setOf(3,4)
            every { questionRepository.findAllQuestionIds(any()) } returns setOf(3,4)

            // when, then
            assertThatThrownBy { questionOpenDomainService.issueQuestion(member.id, LocalDate.now()) }
                .isInstanceOf(QuestionExhaustedException::class.java)

            verify { userCustomizedQuestionCategoryRepository.findAllSelectedCategoryIds(any()) }
            verify(exactly = 0) { questionCategoryRepository.findAllIds() }
            verify { questionRepository.findAllQuestionIds(any()) }
            verify { questionOpenRepository.findAllIssuedQuestionIds(any()) }
            verify(exactly = 0) { questionOpenRepository.save(any()) }
        }
    }
}