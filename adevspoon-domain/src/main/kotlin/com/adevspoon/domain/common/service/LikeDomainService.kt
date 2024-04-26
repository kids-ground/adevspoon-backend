package com.adevspoon.domain.common.service

import com.adevspoon.domain.common.annotation.DomainService
import com.adevspoon.domain.common.entity.LikeEntity
import com.adevspoon.domain.common.repository.LikeRepository
import com.adevspoon.domain.member.domain.UserEntity
import com.adevspoon.domain.member.exception.MemberNotFoundException
import com.adevspoon.domain.member.repository.UserRepository
import org.springframework.data.repository.findByIdOrNull
import org.springframework.transaction.annotation.Transactional
import java.util.*

@DomainService
class LikeDomainService(
    private val likeRepository: LikeRepository,
    private val userRepository: UserRepository
) {
    @Transactional
    fun isUserLikedPost(userId: Long, boardPostId: Long): Boolean {
        return likeRepository.exitsByUserIdAndBoardPostId(userId, boardPostId)
    }


    @Transactional(readOnly = true)
    fun getLikedPostIdsByUser(loginUserId: Long, boardPostIds: List<Long>): List<Long> {
        return likeRepository.findLikedPostIdsByUser(loginUserId, boardPostIds)
    }

    @Transactional
    fun toggleLike(type: String, contentId: Long, isLike: Boolean, loginUserId: Long) {
        if (isLike) {
            deleteLike(type, contentId, loginUserId)
        } else {
            val user = userRepository.findByIdOrNull(loginUserId) ?: throw MemberNotFoundException()
            createLike(type, contentId, user)
        }
    }

    private fun deleteLike(type: String, contentId: Long, userId: Long) {
        likeRepository.findByTypeAndUserId(type, userId, contentId)?.let { likeRepository.delete(it) }
    }

    private fun createLike(type: String, contentId: Long, user: UserEntity) {
        val newLike = LikeEntity(
            user = user,
            postType = type.lowercase(Locale.getDefault()),
            boardPostId = if (type == "BOARD_POST") contentId else null,
            boardCommentId = if (type == "BOARD_COMMENT") contentId else null
        )
        likeRepository.save(newLike)
    }

}
