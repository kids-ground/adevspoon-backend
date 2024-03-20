package com.adevspoon.api.member.dto.request


// TODO: Converter로 Enum값 value로 바꿀 필요있음
enum class FavoriteType(
    val value: String
) {
    ALL("all"),
    ANSWER("answer"),
    BOARD_POST("board_post")
}