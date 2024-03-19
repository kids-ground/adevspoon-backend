package com.adevspoon.api.member.dto.request

enum class FavoriteType(
    val value: String
) {
    ALL("all"),
    ANSWER("answer"),
    BOARD_POST("board_post")
}