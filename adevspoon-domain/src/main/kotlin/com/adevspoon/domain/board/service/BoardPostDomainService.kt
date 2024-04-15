package com.adevspoon.domain.board.service

import com.adevspoon.domain.board.domain.BoardPostEntity
import com.adevspoon.domain.board.dto.response.BoardPost
import com.adevspoon.domain.board.exception.BoardPostNotFoundException
import com.adevspoon.domain.board.exception.BoardTageNotFoundException
import com.adevspoon.domain.board.repository.BoardPostRepository
import com.adevspoon.domain.board.repository.BoardTagRepository
import com.adevspoon.domain.common.annotation.DomainService
import com.adevspoon.domain.common.utils.CursorPageable
import com.adevspoon.domain.common.utils.PageWithCursor
import com.adevspoon.domain.member.exception.MemberNotFoundException
import com.adevspoon.domain.member.service.MemberDomainService
import org.springframework.data.domain.Page
import org.springframework.data.domain.Pageable
import org.springframework.data.repository.findByIdOrNull
import org.springframework.transaction.annotation.Transactional

@DomainService
class BoardPostDomainService(
    val memberDomainService: MemberDomainService,
    val boardPostRepository: BoardPostRepository,
    val boardTagRepository: BoardTagRepository,
) {
    @Transactional
    fun registerBoardPost(userId: Long, tagId: Int, title: String, content: String): BoardPost {
        val user = memberDomainService.getUserEntity(userId)
        val memberProfile = memberDomainService.getMemberProfile(userId)
        val tag = boardTagRepository.findByIdOrNull(tagId) ?: throw BoardTageNotFoundException()
        val boardPost = BoardPostEntity(user = user, tag = tag, title = title, content = content, likeCount = 0, commentCount = 0, viewCount = 0)
        val savedBoardPost = boardPostRepository.save(boardPost)
        return BoardPost.from(savedBoardPost, memberProfile)
    }

    @Transactional
    fun getBoardPost(postId: Long): BoardPost {
        val boardPost = boardPostRepository.findByIdOrNull(postId) ?: throw BoardPostNotFoundException()
        boardPost.increaseViewCount()
        boardPostRepository.save(boardPost)

        val userId = boardPost.user?.id
        userId?.let { BoardPost.from(boardPost, memberDomainService.getMemberProfile(it)) } ?: throw MemberNotFoundException()

        val memberProfile = memberDomainService.getMemberProfile(userId)
        return BoardPost.from(boardPost, memberProfile)
    }

    fun getBoardPostsByTags(tags: List<Int>, pageSize: Int, startPostId: Long?, targetUserId: Long?): PageWithCursor<BoardPost> {
        val pageable = CursorPageable(startPostId, pageSize)
        val page = fetchPostBasedOnTageExistence(tags, startPostId, targetUserId, pageable)

        val boardPosts = page.content
        val nextCursorId = if (boardPosts.size < pageSize) null else page.lastOrNull()?.id

        return PageWithCursor(
            data = boardPosts.map { boardPost ->
                val userId = boardPost.user?.id
                userId?.let { BoardPost.from(boardPost, memberDomainService.getMemberProfile(it)) } ?: throw MemberNotFoundException()
            },
            nextCursorId = nextCursorId
        )
    }

    private fun fetchPostBasedOnTageExistence(tags: List<Int>, startPostId: Long?, targetUserId: Long?, pageable: Pageable) : Page<BoardPostEntity> {
        if (tags.isEmpty()) {
            return fetchAllPosts(startPostId, targetUserId, pageable)
        }
        return fetchPostsByTags(tags, startPostId, targetUserId, pageable)
    }

    private fun fetchAllPosts(startPostId: Long?, targetUserId: Long?, pageable: Pageable) : Page<BoardPostEntity> {
        return boardPostRepository.findAllBoardPosts(startPostId, targetUserId, pageable)
    }

    private fun fetchPostsByTags(tags: List<Int>, startPostId: Long?, targetUserId: Long?, pageable: Pageable) : Page<BoardPostEntity> {
        return boardPostRepository.findByTagsAndUserIdWitchCursor(tags, startPostId, targetUserId, pageable)
    }
}
