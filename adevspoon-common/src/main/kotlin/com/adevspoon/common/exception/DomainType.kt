package com.adevspoon.common.exception

enum class DomainType(
    private val number: Int,
) {
    COMMON(0),
    AUTH(0),
    MEMBER(1),
    TECH_QUESTION(2),
    POST(3),

    ETC(98),

    EXTERNAL_STORAGE(998),
    EXTERNAL_OAUTH(999);

    infix fun code(status: Int): Error {
        return Error(number, status)
    }

    data class Error(
        val domainNo: Int,
        val status: Int,
        var no: Int = 0,
    ) {
        infix fun no(number: Int): Error {
            no = number
            return this
        }

        val errorCode: Int
            get() = (status * 1000 + domainNo) * 1000 + no
    }
}