package com.adevspoon.api.member.dto.request

import com.adevspoon.api.common.dto.LegacyDtoEnum


enum class FavoriteType: LegacyDtoEnum {
    ALL,
    ANSWER,
    BOARD_POST
}