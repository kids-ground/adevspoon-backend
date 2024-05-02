package com.adevspoon.api.member.dto.request

import io.swagger.v3.oas.annotations.media.Schema
import jakarta.validation.constraints.Positive

data class MemberFavoriteListRequest(
    // TODO: ENUM Validation 필요
    @Schema(description = "좋아요한 포스트 타입", example = "answer")
    val type: FavoriteType = FavoriteType.ALL,

    @Schema(description = "시작 postId. 첫 요청이면 null", example = "10")
    val startId: Long?,

    @Schema(description = "몇 개 가지고 올 것인지", example = "10")
    @field:Positive(message = "take는 1 이상이어야 합니다.")
    val take: Long,
)
