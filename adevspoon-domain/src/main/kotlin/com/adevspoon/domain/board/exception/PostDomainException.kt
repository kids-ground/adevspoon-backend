package com.adevspoon.domain.board.exception

import com.adevspoon.common.exception.AdevspoonException

class BoardTageNotFoundException: AdevspoonException(PostDomainErrorCode.BOARD_TAG_NOT_FOUND)