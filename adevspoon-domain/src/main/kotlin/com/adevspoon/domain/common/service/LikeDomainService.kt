package com.adevspoon.domain.common.service

import com.adevspoon.domain.common.annotation.DomainService
import com.adevspoon.domain.common.repository.LikeRepository

@DomainService
class LikeDomainService (
    private val likeRepository: LikeRepository
){
    fun isUserLikedPost(userId: Long, boardPostId: Long) : Boolean {
        return likeRepository.exitsByUserIdAndBoardPostId(userId, boardPostId)
    }

    fun getLikedPostIdsByUser(loginUserId: Long, boardPostIds: List<Long>): List<Long> {
        return likeRepository.findLikedPostIdsByUser(loginUserId, boardPostIds)
    }

}