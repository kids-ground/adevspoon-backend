package com.adevspoon.domain.techQuestion.service

import com.adevspoon.domain.annotation.DomainIntegrationTest
import com.adevspoon.domain.fixture.MemberFixture
import com.adevspoon.domain.fixture.QuestionFixture
import com.adevspoon.domain.member.repository.UserRepository
import com.adevspoon.domain.techQuestion.dto.request.GetTodayQuestion
import com.adevspoon.domain.techQuestion.dto.response.QuestionInfo
import com.adevspoon.domain.techQuestion.repository.QuestionCategoryRepository
import com.adevspoon.domain.techQuestion.repository.QuestionOpenRepository
import com.adevspoon.domain.techQuestion.repository.QuestionRepository
import com.adevspoon.domain.techQuestion.repository.UserCustomizedQuestionCategoryRepository
import org.junit.jupiter.api.Assertions.*
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import org.springframework.beans.factory.annotation.Autowired
import java.time.LocalDate
import java.util.concurrent.CountDownLatch
import java.util.concurrent.Executors


@DomainIntegrationTest
class QuestionDomainServiceIntegrationTest {
    @Autowired private lateinit var questionDomainService: QuestionDomainService

    @Autowired private lateinit var questionCategoryRepository: QuestionCategoryRepository
    @Autowired private lateinit var questionRepository: QuestionRepository
    @Autowired private lateinit var questionOpenRepository: QuestionOpenRepository
    @Autowired private lateinit var userRepository: UserRepository
    @Autowired private lateinit var userCustomizedQuestionCategoryRepository: UserCustomizedQuestionCategoryRepository
    @Autowired private lateinit var questionOpenDomainService: QuestionOpenDomainService

    @BeforeEach
    fun setup() {
        val user = MemberFixture.createMember(1)
        val question1 = QuestionFixture.createQuestion(1)
        val question2 = QuestionFixture.createQuestion(2)
        val questionCategory = QuestionFixture.createQuestionCategory(1)

        userRepository.save(user)
        questionRepository.save(question1)
        questionRepository.save(question2)
        questionCategoryRepository.save(questionCategory)
    }

    @Test
    fun `getOrCreateTodayQuestion - 문제 발급 동시성 테스트`() {
        val numThreads = 3
        val latch = CountDownLatch(numThreads)
        val executor = Executors.newFixedThreadPool(numThreads)
        val questionInfoList = mutableListOf<QuestionInfo>()

        for (i in 0 until numThreads) {
            executor.execute {
                val questionInfo =
                    questionDomainService.getOrCreateTodayQuestion(GetTodayQuestion(1, LocalDate.now()))
                questionInfoList.add(questionInfo)
                // 동시성으로 create 메서드 호출
                latch.countDown()
            }
        }

        latch.await()
        executor.shutdown()
        val questionId = questionInfoList[0].questionId

        assertAll({
            questionInfoList.forEach {
                assertEquals(it.questionId, questionId)
            }
        })
    }
}