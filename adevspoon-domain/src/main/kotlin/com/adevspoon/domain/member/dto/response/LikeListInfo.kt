package com.adevspoon.domain.member.dto.response

data class LikeListInfo(
    val list: List<LikeInfo>,
    val nextStartId: Long?
)