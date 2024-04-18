package com.adevspoon.domain.board.exception

import com.adevspoon.common.exception.AdevspoonException

class BoardTageNotFoundException: AdevspoonException(PostDomainErrorCode.BOARD_TAG_NOT_FOUND)
class BoardPostNotFoundException: AdevspoonException(PostDomainErrorCode.BOARD_POST_NOT_FOUND)
class BoardPostOwnershipException: AdevspoonException(PostDomainErrorCode.BOARD_POST_EDIT_UNAUTHORIZED)