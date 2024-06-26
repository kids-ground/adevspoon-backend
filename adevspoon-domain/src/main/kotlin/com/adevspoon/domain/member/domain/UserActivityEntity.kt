package com.adevspoon.domain.member.domain

import com.adevspoon.domain.common.entity.BaseEntity
import jakarta.persistence.Column
import jakarta.persistence.Entity
import jakarta.persistence.Id
import jakarta.persistence.Table
import jakarta.validation.constraints.NotNull

@Entity
@Table(name = "UserActivity", schema = "adevspoon")
class UserActivityEntity(
    @Id
    @Column(name = "userId", nullable = false)
    var id: Long? = null,

    @NotNull
    @Column(name = "cumulativeAnswerCount", nullable = false)
    var cumulativeAnswerCount: Int = 0,

    @NotNull
    @Column(name = "consecutiveAnswerCount", nullable = false)
    var consecutiveAnswerCount: Int = 0,

    @NotNull
    @Column(name = "cumulativeAttendanceCount", nullable = false)
    var cumulativeAttendanceCount: Int = 1,

    @NotNull
    @Column(name = "consecutiveAttendanceCount", nullable = false)
    var consecutiveAttendanceCount: Int = 1,

    @NotNull
    @Column(name = "answerCountWithinWeek", nullable = false)
    var answerCountWithinWeek: Int = 0,

    @NotNull
    @Column(name = "maxConsecutiveAnswerCount", nullable = false)
    var maxConsecutiveAnswerCount: Int = 0,

    @NotNull
    @Column(name = "maxConsecutiveAttendanceCount", nullable = false)
    var maxConsecutiveAttendanceCount: Int = 0,

    @NotNull
    @Column(name = "boardPostCount", nullable = false)
    var boardPostCount: Int = 0,

    @NotNull
    @Column(name = "answerDatabase", nullable = false)
    var answerDatabase: Int = 0,

    @NotNull
    @Column(name = "answerNetwork", nullable = false)
    var answerNetwork: Int = 0,

    @NotNull
    @Column(name = "answerOperatingSystem", nullable = false)
    var answerOperatingSystem: Int = 0,

    @NotNull
    @Column(name = "answerDataStructure", nullable = false)
    var answerDataStructure: Int = 0,

    @NotNull
    @Column(name = "answerAlgorithm", nullable = false)
    var answerAlgorithm: Int = 0,

    @NotNull
    @Column(name = "answerMachineLearning", nullable = false)
    var answerMachineLearning: Int = 0,

    @NotNull
    @Column(name = "answerClang", nullable = false)
    var answerClang: Int = 0
): BaseEntity() {
    fun fieldValue(fieldName: String): Int {
        return when (fieldName) {
            "cumulativeAnswerCount" -> cumulativeAnswerCount
            "consecutiveAnswerCount" -> consecutiveAnswerCount
            "cumulativeAttendanceCount" -> cumulativeAttendanceCount
            "consecutiveAttendanceCount" -> consecutiveAttendanceCount
            "maxConsecutiveAnswerCount" -> maxConsecutiveAnswerCount
            "maxConsecutiveAttendanceCount" -> maxConsecutiveAttendanceCount
            else -> 0
        }
    }
}