package com.adevspoon.domain.techQuestion.service

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
import io.mockk.mockk
import io.mockk.verify
import org.junit.jupiter.api.Test

import org.junit.jupiter.api.Assertions.*
import org.junit.jupiter.api.assertThrows
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Nested
import org.springframework.data.repository.findByIdOrNull
import java.time.LocalDate
import java.time.LocalDateTime

class QuestionDomainServiceUnitTest {
    private val questionCategoryRepository = mockk<QuestionCategoryRepository>()
    private val questionRepository = mockk<QuestionRepository>()
    private val questionOpenRepository = mockk<QuestionOpenRepository>()
    private val userRepository = mockk<UserRepository>()
    private val userCustomizedQuestionCategoryRepository = mockk<UserCustomizedQuestionCategoryRepository>()
    private val questionOpenDomainService = mockk<QuestionOpenDomainService>()

    private lateinit var questionDomainService: QuestionDomainService

    @BeforeEach
    fun setup() {
        questionDomainService = QuestionDomainService(
            questionCategoryRepository,
            questionRepository,
            questionOpenRepository,
            userRepository,
            userCustomizedQuestionCategoryRepository,
            questionOpenDomainService
        )
    }

    @Nested
    inner class GetQuestion {
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
        fun `SCCESS - 문제 가져오기 성공`() {
            // given
            val issuedQuestion = QuestionFixture.createQuestionOpen(1, question1, user = user)
            every { questionOpenRepository.findByQuestionAndUser(question1, user) } returns issuedQuestion

            // when
            val question = questionDomainService.getQuestion(user.id, question1.id)

            // then
            assertEquals(question.questionId, question1.id)
            verify { questionOpenRepository.findByQuestionAndUser(question1, user) }
        }

        @Test
        fun `FAIL - 발급 받지 않은 문제 요청`() {
            // given
            every { questionOpenRepository.findByQuestionAndUser(question1, user) } returns null

            // when, then
            assertThrows<QuestionNotOpenedException> {
                questionDomainService.getQuestion(user.id, question1.id)
            }

            verify { questionOpenRepository.findByQuestionAndUser(question1, user) }
        }
    }

    @Nested
    inner class GetOrCreateTodayQuestion {
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
        fun `SUCCESS - 문제 발급 & 응답`() {
            val today = LocalDate.now()
            val latestIssuedQuestion =
                QuestionFixture.createQuestionOpen(1, question1, user = user, openDate = LocalDateTime.now().minusDays(1))
            val newIssuedQuestionInfo = QuestionFixture.createQuestionInfo(questionId = question2.id)
            every { questionOpenRepository.findLatest(user) } returns latestIssuedQuestion
            every { questionOpenDomainService.issueQuestion(user.id , today) } returns newIssuedQuestionInfo

            val questionInfo = questionDomainService.getOrCreateTodayQuestion(GetTodayQuestion(user.id, today))

            assertEquals(questionInfo.questionId, question2.id)

            verify { questionOpenRepository.findLatest(user) }
            verify { questionOpenDomainService.issueQuestion(any(), any()) }
        }

        @Test
        fun `SUCCESS - 기존 문제 응답`() {
            val latestIssuedQuestion =
                QuestionFixture.createQuestionOpen(1, question1, user = user, openDate = LocalDateTime.now())
            val newIssuedQuestionInfo = QuestionFixture.createQuestionInfo(questionId = question2.id)
            every { questionOpenRepository.findLatest(user) } returns latestIssuedQuestion
            every { questionOpenDomainService.issueQuestion(any(), any()) } returns newIssuedQuestionInfo

            val questionInfo = questionDomainService.getOrCreateTodayQuestion(GetTodayQuestion(1, LocalDate.now()))

            assertEquals(questionInfo.questionId, question1.id)

            verify { questionOpenRepository.findLatest(user) }
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
        fun `SUCCESS - 사용자의 문제 카테고리 가져오기 (고갈, 선택 포함)`() {
            // given
            every { questionCategoryRepository.findAll() } returns listOf(questionCategory1, questionCategory2, questionCategory3)
            every { userCustomizedQuestionCategoryRepository.findAllSelectedCategory(user) } returns listOf(questionCategory1, questionCategory2)

            every { questionRepository.findQuestionCountGroupByCategory() } returns listOf(
                QuestionFixture.createQuestionCount(1, 2),
                QuestionFixture.createQuestionCount(2, 1),
                QuestionFixture.createQuestionCount(3, 1),
            )
            every { questionOpenRepository.findIssuedQuestionGroupByCategory(user) } returns listOf(
                QuestionFixture.createQuestionCount(1, 2),
            )

            // when
            var categories = questionDomainService.getQuestionCategories(user.id)
            categories = categories.sortedWith(compareBy { it.id })

            // then
            assertEquals(categories.size, 3, "문제 카테고리 개수는 총 3개")
            assertEquals(categories[0].id, 1L)
            assertEquals(categories[0].depleted, true, "카테고리 1은 문제 고갈됨")
            assertEquals(categories[0].selected, true, "카테고리 1은 선택됨")

            assertEquals(categories[1].id, 2L)
            assertEquals(categories[1].depleted, false, "카테고리 2는 고갈되지 않음")
            assertEquals(categories[1].selected, true, "카테고리 2는 선택됨")

            assertEquals(categories[2].id, 3L)
            assertEquals(categories[2].depleted, false, "카테고리 3은 고갈되지 않음")
            assertEquals(categories[2].selected, false, "카테고리 3은 선택하지 않음")
        }
    }
}