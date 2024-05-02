package com.adevspoon.api.member.dto.response

import com.adevspoon.domain.member.dto.response.LikeInfo
import io.swagger.v3.oas.annotations.media.Schema
import java.time.LocalDate

data class MemberFavoriteResponse(
    val id: Long,
    @Schema(description = "포스트 타입", example = "answer")
    val postType: PostType = PostType.ANSWER,
    val postId: Long,
    @Schema(description = "Question의 제목")
    val title: String,
    @Schema(description = "Answer의 내용")
    val content: String,
    @Schema(description = "좋아요한 날짜")
    val date: LocalDate,
    @Schema(description = "작성자 정보")
    val writer: MemberProfileResponse,
    val isLiked: Boolean?,
    val likeCount: Int,
) {
    companion object {
        fun from(likeInfo: LikeInfo): MemberFavoriteResponse {
            return MemberFavoriteResponse(
                id = likeInfo.id,
                postId = likeInfo.postId,
                title = likeInfo.title,
                content = likeInfo.content,
                date = likeInfo.date,
                writer = MemberProfileResponse.from(likeInfo.writer),
                isLiked = likeInfo.isLiked,
                likeCount = likeInfo.likeCount,
            )
        }
    }
}
