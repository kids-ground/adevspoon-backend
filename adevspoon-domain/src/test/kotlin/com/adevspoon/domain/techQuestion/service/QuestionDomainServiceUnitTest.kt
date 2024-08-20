package com.adevspoon.domain.techQuestion.service

import com.adevspoon.domain.annotation.UnitTest
import com.adevspoon.domain.fixture.MemberFixture
import com.adevspoon.domain.fixture.QuestionFixture
import com.adevspoon.domain.member.domain.UserEntity
import com.adevspoon.domain.member.repository.UserRepository
import com.adevspoon.domain.techQuestion.domain.QuestionCategoryEntity
import com.adevspoon.domain.techQuestion.domain.QuestionEntity
import com.adevspoon.domain.techQuestion.dto.request.GetTodayQuestion
import com.adevspoon.domain.techQuestion.exception.QuestionNotOpenedException
import com.adevspoon.domain.techQuestion.repository.QuestionCategoryRepository
import com.adevspoon.domain.techQuestion.repository.QuestionOpenRepository
import com.adevspoon.domain.techQuestion.repository.QuestionRepository
import com.adevspoon.domain.techQuestion.repository.UserCustomizedQuestionCategoryRepository
import io.mockk.every
import io.mockk.impl.annotations.InjectMockKs
import io.mockk.impl.annotations.MockK
import io.mockk.verify
import org.assertj.core.api.Assertions.*
import org.junit.jupiter.api.Test

import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Nested
import org.springframework.data.repository.findByIdOrNull
import java.time.LocalDate
import java.time.LocalDateTime

@UnitTest
class QuestionDomainServiceUnitTest {
    @MockK private lateinit var questionCategoryRepository: QuestionCategoryRepository
    @MockK private lateinit var questionRepository: QuestionRepository
    @MockK private lateinit var questionOpenRepository: QuestionOpenRepository
    @MockK private lateinit var userRepository: UserRepository
    @MockK private lateinit var userCustomizedQuestionCategoryRepository: UserCustomizedQuestionCategoryRepository
    @MockK private lateinit var questionOpenDomainService: QuestionOpenDomainService

    @InjectMockKs private lateinit var questionDomainService: QuestionDomainService

    @Nested
    inner class GetQuestionUnitTests {
        private lateinit var user: UserEntity
        private lateinit var question1: QuestionEntity
        private lateinit var question2: QuestionEntity

        @BeforeEach
        fun setup() {
            user = MemberFixture.createMember(1)
            question1 = QuestionFixture.createQuestion(1)
            question2 = QuestionFixture.createQuestion(2)
            val questionCategory = QuestionFixture.createQuestionCategory(1)

            every { userRepository.findByIdOrNull(1) } returns user
            every { questionRepository.findByIdOrNull(1) } returns question1
            every { questionRepository.findByIdOrNull(2) } returns question2
            every { questionCategoryRepository.findByIdOrNull(any()) } returns questionCategory
        }

        @Test
        fun `SCCESS - 발급 받은 문제를 문제ID를 이용해 가져온다`() {
            // given
            val issuedQuestion = QuestionFixture.createQuestionOpen(1, question1, user = user)
            every { questionOpenRepository.findByQuestionAndUser(question1, user) } returns issuedQuestion

            // when
            val question = questionDomainService.getQuestion(user.id, question1.id)

            // then
            assertThat(question.questionId)
                .isEqualTo(question1.id)

            verify { questionOpenRepository.findByQuestionAndUser(question1, user) }
        }

        @Test
        fun `FAIL - 발급 받지 않은 문제 요청 시 예외가 발생한다`() {
            // given
            every { questionOpenRepository.findByQuestionAndUser(question1, user) } returns null

            // when, then
            assertThatThrownBy { questionDomainService.getQuestion(user.id, question1.id) }
                .isInstanceOf(QuestionNotOpenedException::class.java)

            verify { questionOpenRepository.findByQuestionAndUser(question1, user) }
        }
    }

    @Nested
    inner class GetOrCreateTodayQuestionUnitTests {
        private lateinit var user: UserEntity
        private lateinit var question1: QuestionEntity
        private lateinit var question2: QuestionEntity

        @BeforeEach
        fun setup() {
            user = MemberFixture.createMember(1)
            question1 = QuestionFixture.createQuestion(1)
            question2 = QuestionFixture.createQuestion(2)
            val questionCategory = QuestionFixture.createQuestionCategory(1)

            every { userRepository.findByIdOrNull(1) } returns user
            every { questionCategoryRepository.findByIdOrNull(any()) } returns questionCategory
        }

        @Test
        fun `SUCCESS - 오늘자 문제를 아직 발급받지 않았다면 문제를 발급 후 응답한다`() {
            // given
            val today = LocalDate.now()
            val yesterdayDateTime = today.atStartOfDay().minusDays(1)
            val latestIssuedQuestion =
                QuestionFixture.createQuestionOpen(
                    id = 1,
                    question = question1,
                    user = user,
                    openDate = yesterdayDateTime
                )
            val newIssuedQuestionInfo = QuestionFixture.createQuestionInfo(questionId = question2.id)
            every { questionOpenRepository.findLatestWithQuestionAndAnswer(user) } returns latestIssuedQuestion
            every { questionOpenDomainService.issueQuestion(user.id, today) } returns newIssuedQuestionInfo

            // when
            val questionInfo = questionDomainService.getOrCreateTodayQuestion(GetTodayQuestion(user.id, today))

            // then
            assertThat(questionInfo.questionId)
                .isEqualTo(question2.id)

            verify { questionOpenRepository.findLatestWithQuestionAndAnswer(user) }
            verify { questionOpenDomainService.issueQuestion(any(), any()) }
        }

        @Test
        fun `SUCCESS - 이미 오늘자 문제를 발급 받았다면 해당 문제를 응답한다`() {
            // given
            val today = LocalDate.now()
            val latestIssuedQuestion =
                QuestionFixture.createQuestionOpen(1, question1, user = user, openDate = today.atStartOfDay())
            val newIssuedQuestionInfo = QuestionFixture.createQuestionInfo(questionId = question2.id)
            every { questionOpenRepository.findLatestWithQuestionAndAnswer(user) } returns latestIssuedQuestion
            every { questionOpenDomainService.issueQuestion(any(), any()) } returns newIssuedQuestionInfo

            // when
            val questionInfo = questionDomainService.getOrCreateTodayQuestion(GetTodayQuestion(1, today))

            // then
            assertThat(questionInfo.questionId)
                .isEqualTo(question1.id)

            verify { questionOpenRepository.findLatestWithQuestionAndAnswer(user) }
            verify(exactly = 0) { questionOpenDomainService.issueQuestion(any(), any()) }
        }
    }

    @Nested
    inner class GetQuestionCategories {
        private lateinit var user: UserEntity
        private lateinit var questionCategory1: QuestionCategoryEntity
        private lateinit var questionCategory2: QuestionCategoryEntity
        private lateinit var questionCategory3: QuestionCategoryEntity
        private lateinit var question1: QuestionEntity
        private lateinit var question2: QuestionEntity
        private lateinit var question3: QuestionEntity
        private lateinit var question4: QuestionEntity

        @BeforeEach
        fun setup() {
            user = MemberFixture.createMember(1)
            questionCategory1 = QuestionFixture.createQuestionCategory(1)
            questionCategory2 = QuestionFixture.createQuestionCategory(2)
            questionCategory3 = QuestionFixture.createQuestionCategory(3)
            question1 = QuestionFixture.createQuestion(1, categoryId = 1)
            question2 = QuestionFixture.createQuestion(1, categoryId = 1)
            question3 = QuestionFixture.createQuestion(1, categoryId = 2)
            question4 = QuestionFixture.createQuestion(1, categoryId = 3)

            every { userRepository.findByIdOrNull(1) } returns user
        }

        @Test
        fun `SUCCESS - 선택 여부와 고갈 여부를 포함한 문제 카테고리 정보를 모두 가져온다`() {
            // given
            every { questionCategoryRepository.findAll() } returns listOf(
                questionCategory1,
                questionCategory2,
                questionCategory3
            )
            every { userCustomizedQuestionCategoryRepository.findAllSelectedCategory(user) } returns listOf(
                questionCategory1,
                questionCategory2
            )

            every { questionRepository.findQuestionCountGroupByCategory() } returns listOf(
                QuestionFixture.createQuestionCount(1, 2),
                QuestionFixture.createQuestionCount(2, 1),
                QuestionFixture.createQuestionCount(3, 1),
            )
            every { questionOpenRepository.findIssuedQuestionGroupByCategory(user) } returns listOf(
                QuestionFixture.createQuestionCount(1, 2),
            )

            // when
            val categories = questionDomainService.getQuestionCategories(user.id).sortedWith(compareBy { it.id })

            // then
            assertThat(categories).hasSize(3)
                .extracting("id", "depleted", "selected")
                .containsExactlyInAnyOrder(
                    tuple(1L, true, true),
                    tuple(2L, false, true),
                    tuple(3L, false, false)
                )
        }
    }
}