package com.adevspoon.domain.board.exception

import com.adevspoon.common.exception.AdevspoonException
import com.adevspoon.domain.board.exception.PostDomainErrorCode.*

class BoardTagNotFoundException(tagId: String): AdevspoonException(
    BOARD_TAG_NOT_FOUND,
    detailReason = BOARD_TAG_NOT_FOUND.message + " 존재하지 않는 tagId: $tagId")
class BoardPostNotFoundException(postId: String): AdevspoonException(
    BOARD_POST_NOT_FOUND,
    detailReason = BOARD_POST_NOT_FOUND.message + " 존재하지 않는 postId: $postId")
class BoardPostOwnershipException(postOwnerId: String, loginUserId: String): AdevspoonException(
    BOARD_POST_EDIT_UNAUTHORIZED,
    detailReason = BOARD_POST_EDIT_UNAUTHORIZED.message + " postOwnerId: $postOwnerId, loginUserId: $loginUserId")
class BoardPostInvalidReturnException: AdevspoonException(BOARD_POST_INVALID_RETURN)

class BoardCommentNotFoundException(commentId: String) : AdevspoonException(
    BOARD_COMMENT_NOT_FOUND,
    detailReason = BOARD_COMMENT_NOT_FOUND.message + " 존재하지 않는 commentId: $commentId"
)

class BoardPostCommentException(commentOwnerId: String, loginUserId: String): AdevspoonException(
    BOARD_COMMENT_EDIT_UNAUTHORIZED,
    detailReason = BOARD_COMMENT_EDIT_UNAUTHORIZED.message + " commentOwnerId: $commentOwnerId, loginUserId: $loginUserId")

class SelfReportException : AdevspoonException(SELF_REPORT_NOT_ALLOWED)
class DuplicateReportException(type: String, contentId: Long) : AdevspoonException(
    ALREADY_REPORTED,
    detailReason = ALREADY_REPORTED.message + " 신고된 content type: $type, id: $contentId")

class NegativeLikeCountExceptionForBoard(type: String, contentId: Long) : AdevspoonException(
    MINIMUM_LIKE_COUNT,
    detailReason = MINIMUM_LIKE_COUNT.message + " type: $type, id: $contentId"
)

class UnsupportedTypeForContentAccess() : AdevspoonException(UNSUPPORTED_TYPE_FOR_CONTENT_ACCESS)