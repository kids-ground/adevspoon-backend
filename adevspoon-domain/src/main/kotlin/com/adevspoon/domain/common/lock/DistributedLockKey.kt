package com.adevspoon.domain.common.lock

private const val PREFIX_ISSUE_QUESTION = "issue_question:"


sealed interface DistributedLockKey {
    val lockKey: String
    val timeout: Int
        get() = 3
    val leaseTime: Int
        get() = -1
}

interface IssueQuestionLockKey : DistributedLockKey {
    val keyMemberId: Long

    override val lockKey: String
        get() = "$PREFIX_ISSUE_QUESTION:$keyMemberId"
}