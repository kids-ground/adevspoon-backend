package com.adevspoon.domain.board.service

import com.adevspoon.domain.board.domain.BoardPostEntity
import com.adevspoon.domain.board.dto.response.BoardPost
import com.adevspoon.domain.board.exception.BoardPostNotFoundException
import com.adevspoon.domain.board.exception.BoardPostOwnershipException
import com.adevspoon.domain.board.exception.BoardTageNotFoundException
import com.adevspoon.domain.board.repository.BoardPostRepository
import com.adevspoon.domain.board.repository.BoardTagRepository
import com.adevspoon.domain.common.annotation.DomainService
import com.adevspoon.domain.common.service.LikeDomainService
import com.adevspoon.domain.common.utils.CursorPageable
import com.adevspoon.domain.common.utils.PageWithCursor
import com.adevspoon.domain.member.service.MemberDomainService
import org.springframework.data.domain.Page
import org.springframework.data.domain.Pageable
import org.springframework.data.repository.findByIdOrNull
import org.springframework.transaction.annotation.Transactional

@DomainService
class BoardPostDomainService(
    val boardPostRepository: BoardPostRepository,
    val boardTagRepository: BoardTagRepository,
    val memberDomainService: MemberDomainService,
    val likeDomainService: LikeDomainService
) {
    @Transactional
    fun registerBoardPost(title: String, content: String, tagId: Int, userId: Long): BoardPost {
        val user = memberDomainService.getUserEntity(userId)
        val memberProfile = memberDomainService.getMemberProfile(userId)
        val tag = boardTagRepository.findByIdOrNull(tagId) ?: throw BoardTageNotFoundException()

        val boardPost = BoardPostEntity(user = user, tag = tag, title = title, content = content, likeCount = 0, commentCount = 0, viewCount = 0)

        val savedBoardPost = boardPostRepository.save(boardPost)
        return BoardPost.from(savedBoardPost, memberProfile, false)
    }

    @Transactional
    fun getBoardPost(postId: Long, userId: Long): BoardPost {
        val boardPost = boardPostRepository.findByIdOrNull(postId) ?: throw BoardPostNotFoundException()
        boardPost.increaseViewCount()
        boardPostRepository.save(boardPost)

        val isUserLikedBoardPost = likeDomainService.isUserLikedPost(userId, boardPost.id)
        val memberProfile = memberDomainService.getMemberProfile(boardPost.user.id)

        return BoardPost.from(boardPost, memberProfile, isUserLikedBoardPost)
    }

    @Transactional
    fun updateBoardPost(title: String?, content: String, tagId: Int, postId: Long, userId: Long): BoardPost {
        val boardPost = boardPostRepository.findByIdOrNull(postId) ?: throw BoardPostNotFoundException()

        if (boardPost.user.id != userId) throw BoardPostOwnershipException()

        if (boardPost.tag.id != tagId) {
            val newTag = boardTagRepository.findByIdOrNull(tagId) ?: throw BoardTageNotFoundException()
            boardPost.updateTag(newTag)
        }

        boardPost.updateTitleAndContent(title, content)
        boardPostRepository.save(boardPost)

        val isUserLikedBoardPost = likeDomainService.isUserLikedPost(userId, boardPost.id)
        val memberProfile = memberDomainService.getMemberProfile(boardPost.user.id)

        return BoardPost.from(boardPost, memberProfile, isUserLikedBoardPost)
    }

    @Transactional(readOnly = true)
    fun getBoardPostsWithCriteria(tags: List<Int>, pageSize: Int, startPostId: Long?, targetUserId: Long?, loginUserId: Long): PageWithCursor<BoardPost> {
        val pageable = CursorPageable(pageSize)
        val page = fetchPostBasedOnTageExistence(tags, startPostId, targetUserId, pageable)

        val boardPosts = page.content
        val nextCursorId = if (boardPosts.size < pageSize) null else page.lastOrNull()?.id
        val likedPostIds = getLikedPostsByUser(loginUserId, boardPosts.map { it.id }.toList()).toSet()
        val boardPostDto = boardPosts.map { boardPost ->
            val isUserLikedBoardPost = likedPostIds.contains(boardPost.id)
            BoardPost.from(boardPost, memberDomainService.getMemberProfile(boardPost.user.id), isUserLikedBoardPost)
        }

        return PageWithCursor(
            data = boardPostDto,
            nextCursorId = nextCursorId
        )
    }

    private fun getLikedPostsByUser(loginUserId: Long, postIds: List<Long>) : Set<Long> {
        return likeDomainService.getLikedPostIdsByUser(loginUserId, postIds).toSet()
    }

    private fun fetchPostBasedOnTageExistence(tags: List<Int>, startPostId: Long?, targetUserId: Long?, pageable: Pageable) : Page<BoardPostEntity> {
        if (tags.isEmpty()) {
            return retrievePostsIfNoTags(startPostId, targetUserId, pageable)
        }
        return retrievePostsByTagsIfPresent(tags, startPostId, targetUserId, pageable)
    }

    private fun retrievePostsIfNoTags(startPostId: Long?, targetUserId: Long?, pageable: Pageable) : Page<BoardPostEntity> {
        return boardPostRepository.findWithNoTagsAndUserIdWithCursor(startPostId, targetUserId, pageable)
    }

    private fun retrievePostsByTagsIfPresent(tags: List<Int>, startPostId: Long?, targetUserId: Long?, pageable: Pageable) : Page<BoardPostEntity> {
        return boardPostRepository.findByTagsAndUserIdWithCursor(tags, startPostId, targetUserId, pageable)
    }
}
