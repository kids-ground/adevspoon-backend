package com.adevspoon.domain.common.utils

class PageWithCursor<T> (
    val data: List<T>,
    val nextCursorId: Long?
)