package com.adevspoon.domain.board.service

import com.adevspoon.domain.board.domain.BoardCommentEntity
import com.adevspoon.domain.board.domain.BoardPostEntity
import com.adevspoon.domain.board.dto.request.*
import com.adevspoon.domain.board.dto.response.BoardPost
import com.adevspoon.domain.board.exception.*
import com.adevspoon.domain.board.repository.BoardCommentRepository
import com.adevspoon.domain.board.repository.BoardPostRepository
import com.adevspoon.domain.board.repository.BoardTagRepository
import com.adevspoon.domain.common.annotation.*
import com.adevspoon.domain.common.entity.BaseEntity
import com.adevspoon.domain.common.entity.ReportEntity
import com.adevspoon.domain.common.entity.enums.ReportReason
import com.adevspoon.domain.common.event.ReportEvent
import com.adevspoon.domain.common.repository.ReportRepository
import com.adevspoon.domain.common.service.LikeDomainService
import com.adevspoon.domain.common.utils.CursorPageable
import com.adevspoon.domain.common.utils.PageWithCursor
import com.adevspoon.domain.member.domain.UserEntity
import com.adevspoon.domain.member.exception.MemberNotFoundException
import com.adevspoon.domain.member.repository.UserRepository
import com.adevspoon.domain.member.service.MemberDomainService
import org.springframework.data.repository.findByIdOrNull
import org.springframework.transaction.annotation.Transactional
import java.util.*

@DomainService
class BoardPostDomainService(
    val boardPostRepository: BoardPostRepository,
    val boardTagRepository: BoardTagRepository,
    val boardCommentRepository: BoardCommentRepository,
    val reportRepository: ReportRepository,
    val userRepository: UserRepository,
    val memberDomainService: MemberDomainService,
    val likeDomainService: LikeDomainService
) {
    @ActivityEvent(ActivityEventType.BOARD_POST)
    @Transactional
    fun registerBoardPost(request: RegisterPostRequestDto, userId: Long): BoardPost {
        val user = getUserEntity(userId)
        val tag = boardTagRepository.findByIdOrNull(request.tagId)
            ?: throw BoardTagNotFoundException(request.tagId.toString())

        val boardPost = BoardPostEntity(user = user, tag = tag, title = request.title, content = request.content, likeCount = 0, commentCount = 0, viewCount = 0)
        val savedBoardPost = boardPostRepository.save(boardPost)

        return buildBoardPostResponse(savedBoardPost, userId)
    }

    @Transactional
    fun getBoardPost(postId: Long, userId: Long): BoardPost {
        val boardPost = boardPostRepository.findByIdOrNull(postId)
            ?: throw BoardPostNotFoundException(postId.toString())
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
        val memberProfile = memberDomainService.getOtherMemberProfile(boardPost.user.id)

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
        val postsSlice = boardPostRepository.findByTagsAndUserIdWithCursor(request.tags, request.startPostId, request.targetUserId, pageable)

        val boardPosts = postsSlice.content
        val nextCursorId = if (!postsSlice.hasNext()) null else boardPosts.lastOrNull()?.id
        val likedPostIds = getLikedPostsByUser(loginUserId, boardPosts.map { it.id }.toList())
        val boardPostDto = boardPosts.map { boardPost ->
            val isUserLikedBoardPost = likedPostIds.contains(boardPost.id)
            BoardPost.from(boardPost, memberDomainService.getOtherMemberProfile(boardPost.user.id), isUserLikedBoardPost)
        }

        return PageWithCursor(
            data = boardPostDto,
            nextCursorId = nextCursorId
        )
    }

    private fun getLikedPostsByUser(loginUserId: Long, postIds: List<Long>): Set<Long> {
        return likeDomainService.getLikedPostIdsByUser(loginUserId, postIds).toSet()
    }

    @Transactional
    fun deleteById(postId: Long, userId: Long) {
        val boardPost = getBoardPostEntity(postId)
        validatePostOwnership(boardPost, userId)
        boardPostRepository.deleteById(postId)
    }

    @Transactional
    fun toggleBoardLike(request: UpdateLikeStateRequest, userId: Long) {
        when (request.type) {
            "BOARD_POST" -> {
                val boardPost = getBoardPostEntity(request.contentId)
                likeDomainService.togglePostLike(
                    boardPost,
                    getUserEntity(userId),
                    request.like)
            }
            "BOARD_COMMENT" -> {
                val boardComment = getBoardCommentEntity(request.contentId)
                likeDomainService.toggleCommentLike(
                    boardComment,
                    getUserEntity(userId),
                    request.like)
            }
        }
    }

    @Transactional
    @AdminEvent(type = AdminEventType.REPORT)
    fun report(request: CreateReportRequest, userId: Long): ReportEvent {
        val user = getUserEntity(userId)

        val entity = when (request.type) {
            "BOARD_POST" -> getBoardPostEntity(request.contentId)
            "BOARD_COMMENT" -> getBoardCommentEntity(request.contentId)
            else -> throw IllegalArgumentException("Invalid content type")
        }

        checkOwnershipAndGetContent(entity, userId)
        checkReportExistence(request.type.toString(), request.contentId)
        val content = getContent(entity)

        val report = ReportEntity(
            postType = request.type.toString().lowercase(Locale.getDefault()),
            user = user,
            reason = ReportReason.ETC,
            boardPostId = if (request.type == "BOARD_POST") request.contentId else null,
            boardCommentId = if (request.type == "BOARD_COMMENT") request.contentId else null
        )
        reportRepository.save(report)
        return ReportEvent(content = content, report = report)
    }

    private fun checkReportExistence(type: String, contentId: Long) {
        if (reportRepository.existsByPostTypeAndContentId(type, contentId)) {
            throw DuplicateReportException(type, contentId)
        }
    }

    private fun checkOwnershipAndGetContent(contentEntity: BaseEntity, userId: Long){
        if (contentEntity is BoardPostEntity) {
            if (contentEntity.user.id == userId) {
                throw SelfReportException()
            }
        }

        if (contentEntity is BoardCommentEntity) {
            if (contentEntity.user.id == userId) {
                throw SelfReportException()
            }
        }
    }

    private fun getContent(entity: BaseEntity): String {
        return when (entity) {
            is BoardPostEntity -> entity.content
            is BoardCommentEntity -> entity.content
            else -> throw UnsupportedTypeForContentAccess()
        }
    }

    private fun getBoardPostEntity(postId: Long): BoardPostEntity =
        boardPostRepository.findByIdOrNull(postId) ?: throw BoardPostNotFoundException(postId.toString())

    private fun getBoardCommentEntity(commentId: Long): BoardCommentEntity =
        boardCommentRepository.findByIdOrNull(commentId) ?: throw BoardCommentNotFoundException(commentId.toString())

    private fun getUserEntity(userId: Long): UserEntity =
        userRepository.findByIdOrNull(userId) ?: throw MemberNotFoundException()
}
