package com.adevspoon.domain.common.utils

import org.springframework.data.domain.Pageable
import org.springframework.data.domain.Sort

data class CursorPageable(
    private val size: Int,
    private val sort: Sort? = Sort.by(Sort.Direction.DESC, "id")
) : Pageable{
    override fun getPageNumber(): Int = 0 // no user pageNumber

    override fun getPageSize(): Int = size

    override fun getOffset(): Long = 0 // no user offSet

    override fun getSort(): Sort = sort ?: Sort.by(Sort.Direction.DESC, "id")

    override fun next(): Pageable {
        throw UnsupportedOperationException("Cursor based navigation does not support this operation.")
    }

    override fun previousOrFirst(): Pageable {
        throw UnsupportedOperationException("Cursor based navigation does not support this operation.")
    }

    override fun first(): Pageable = this

    override fun withPage(pageNumber: Int): Pageable {
        throw UnsupportedOperationException("Cursor based navigation does not support this operation.")
    }

    override fun hasPrevious(): Boolean = false
}