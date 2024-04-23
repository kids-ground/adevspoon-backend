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