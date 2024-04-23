package com.adevspoon.domain.board.service

import com.adevspoon.domain.board.domain.BoardPostEntity
import com.adevspoon.domain.board.dto.request.GetPostListRequestDto
import com.adevspoon.domain.board.dto.request.RegisterPostRequestDto
import com.adevspoon.domain.board.dto.request.UpdatePostRequestDto
import com.adevspoon.domain.board.dto.response.BoardPost
import com.adevspoon.domain.board.exception.BoardPostNotFoundException
import com.adevspoon.domain.board.exception.BoardPostOwnershipException
import com.adevspoon.domain.board.exception.BoardTagNotFoundException
import com.adevspoon.domain.board.repository.BoardPostRepository
import com.adevspoon.domain.board.repository.BoardTagRepository
import com.adevspoon.domain.common.annotation.DomainService
import com.adevspoon.domain.common.annotation.ActivityEvent
import com.adevspoon.domain.common.annotation.ActivityEventType
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
    @ActivityEvent(ActivityEventType.BOARD_POST)
    @Transactional
    fun registerBoardPost(request: RegisterPostRequestDto, userId: Long): BoardPost {
        val user = memberDomainService.getUserEntity(userId)
        val tag = boardTagRepository.findByIdOrNull(request.tagId) ?: throw BoardTagNotFoundException(request.tagId.toString())

        val boardPost = BoardPostEntity(user = user, tag = tag, title = request.title, content = request.content, likeCount = 0, commentCount = 0, viewCount = 0)
        val savedBoardPost = boardPostRepository.save(boardPost)

        return buildBoardPostResponse(savedBoardPost, userId)
    }

    @Transactional
    fun getBoardPost(postId: Long, userId: Long): BoardPost {
        val boardPost = boardPostRepository.findByIdOrNull(postId) ?: throw BoardPostNotFoundException(postId.toString())
        boardPost.increaseViewCount()
        boardPostRepository.save(boardPost)

        return buildBoardPostResponse(boardPost, userId)
    }

    @Transactional
    fun updateBoardPost(request: UpdatePostRequestDto, userId: Long): BoardPost {
        val boardPost = getBoardPostEntity(request.postId)
        validatePostOwnership(boardPost, userId)
        updatePostTag(boardPost, request.tagId)
        boardPost.updateTitleAndContent(request.title, request.content)
        boardPostRepository.save(boardPost)

        return buildBoardPostResponse(boardPost, userId)
    }

    private fun buildBoardPostResponse(boardPost: BoardPostEntity, userId: Long): BoardPost {
        val isUserLikedBoardPost = likeDomainService.isUserLikedPost(userId, boardPost.id)
        val memberProfile = memberDomainService.getMemberProfile(boardPost.user.id)

        return BoardPost.from(boardPost, memberProfile, isUserLikedBoardPost)
    }

    private fun validatePostOwnership(boardPost: BoardPostEntity, userId: Long) {
        if (boardPost.user.id != userId) {
            throw BoardPostOwnershipException(postOwnerId = boardPost.user.id.toString(), loginUserId = userId.toString())
        }
    }

    private fun updatePostTag(boardPost: BoardPostEntity, tagId: Int?) {
        tagId?.let {
            if (boardPost.tag.id != it) {
                val newTag = boardTagRepository.findByIdOrNull(it) ?: throw BoardTagNotFoundException(tagId.toString())
                boardPost.updateTag(newTag)
            }
        }
    }

    @Transactional(readOnly = true)
    fun getBoardPostsWithCriteria(request: GetPostListRequestDto, loginUserId: Long): PageWithCursor<BoardPost> {
        val pageable = CursorPageable(request.pageSize)
        val page = fetchPostBasedOnTageExistence(request.tags, request.startPostId, request.targetUserId, pageable)

        val boardPosts = page.content
        val nextCursorId = if (boardPosts.size < request.pageSize) null else page.lastOrNull()?.id
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

    private fun getBoardPostEntity(postId: Long) =
        boardPostRepository.findByIdOrNull(postId) ?: throw BoardPostNotFoundException(postId.toString())
}
