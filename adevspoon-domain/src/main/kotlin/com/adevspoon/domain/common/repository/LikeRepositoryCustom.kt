package com.adevspoon.domain.common.repository

import com.adevspoon.domain.common.entity.LikeEntity
import org.springframework.data.domain.Slice

interface LikeRepositoryCustom {
    fun findAnswerLikeList(memberId: Long, startId: Long?, take: Int): Slice<LikeEntity>
}