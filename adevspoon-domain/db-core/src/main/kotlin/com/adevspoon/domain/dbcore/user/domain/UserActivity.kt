package com.adevspoon.domain.dbcore.user.domain

import com.adevspoon.domain.dbcore.domain.BaseEntity
import jakarta.persistence.Column
import jakarta.persistence.Entity
import jakarta.persistence.Id
import jakarta.persistence.Table
import jakarta.validation.constraints.NotNull

@Entity
@Table(name = "UserActivity", schema = "adevspoon")
class UserActivity(
    @Id
    @Column(name = "userId", nullable = false)
    var id: Long? = null,

    @NotNull
    @Column(name = "cumulativeAnswerCount", nullable = false)
    var cumulativeAnswerCount: Int? = null,

    @NotNull
    @Column(name = "consecutiveAnswerCount", nullable = false)
    var consecutiveAnswerCount: Int? = null,

    @NotNull
    @Column(name = "cumulativeAttendanceCount", nullable = false)
    var cumulativeAttendanceCount: Int? = null,

    @NotNull
    @Column(name = "consecutiveAttendanceCount", nullable = false)
    var consecutiveAttendanceCount: Int? = null,

    @NotNull
    @Column(name = "answerCountWithinWeek", nullable = false)
    var answerCountWithinWeek: Int? = null,

    @NotNull
    @Column(name = "maxConsecutiveAnswerCount", nullable = false)
    var maxConsecutiveAnswerCount: Int? = null,

    @NotNull
    @Column(name = "maxConsecutiveAttendanceCount", nullable = false)
    var maxConsecutiveAttendanceCount: Int? = null,

    @NotNull
    @Column(name = "boardPostCount", nullable = false)
    var boardPostCount: Int? = null,

    @NotNull
    @Column(name = "answerDatabase", nullable = false)
    var answerDatabase: Int? = null,

    @NotNull
    @Column(name = "answerNetwork", nullable = false)
    var answerNetwork: Int? = null,

    @NotNull
    @Column(name = "answerOperatingSystem", nullable = false)
    var answerOperatingSystem: Int? = null,

    @NotNull
    @Column(name = "answerDataStructure", nullable = false)
    var answerDataStructure: Int? = null,

    @NotNull
    @Column(name = "answerAlgorithm", nullable = false)
    var answerAlgorithm: Int? = null,

    @NotNull
    @Column(name = "answerMachineLearning", nullable = false)
    var answerMachineLearning: Int? = null,

    @NotNull
    @Column(name = "answerClang", nullable = false)
    var answerClang: Int? = null
): BaseEntity()