package com.adevspoon.domain.common.lock

private const val PREFIX_ISSUE_QUESTION = "issue_question"


sealed interface LockKey {
    val name: String
    val timeout: Int
        get() = 3
    val leaseTime: Int
        get() = -1
}

interface IssueQuestionLockKey : LockKey {
    val keyMemberId: Long

    override val name: String
        get() = "$PREFIX_ISSUE_QUESTION:$keyMemberId"
}