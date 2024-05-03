package com.adevspoon.api.member.service

import com.adevspoon.api.common.annotation.ApplicationService
import com.adevspoon.api.common.util.ImageProcessor
import com.adevspoon.api.member.dto.request.MemberFavoriteListRequest
import com.adevspoon.common.enums.ImageType
import com.adevspoon.api.member.dto.request.MemberProfileUpdateRequest
import com.adevspoon.api.member.dto.response.MemberFavoriteListResponse
import com.adevspoon.api.member.dto.response.MemberProfileResponse
import com.adevspoon.domain.member.dto.request.GetLikeList
import com.adevspoon.domain.member.service.MemberDomainService
import com.adevspoon.infrastructure.storage.dto.FileInfo
import com.adevspoon.infrastructure.storage.service.StorageAdapter
import org.springframework.web.multipart.MultipartFile

@ApplicationService
class MemberService(
    private val memberDomainService: MemberDomainService,
    private val storageAdapter: StorageAdapter,
    private val imageProcessor: ImageProcessor
) {
    fun getProfile(memberId: Long): MemberProfileResponse {
        val memberProfile = memberDomainService.getMemberProfile(memberId)
        return MemberProfileResponse.from(memberProfile)
    }

    fun updateProfile(userId: Long, request: MemberProfileUpdateRequest): MemberProfileResponse {
        val (profileUrl, thumbnailUrl) = request.image
            ?.let(::uploadProfileImage)
            ?: listOf(null, null)
        val memberProfile = memberDomainService
            .updateMemberProfile(request.toMemberUpdateRequireDto(userId, profileUrl, thumbnailUrl))

        return MemberProfileResponse.from(memberProfile)
    }

    fun getLikeList(memberId: Long, request: MemberFavoriteListRequest): MemberFavoriteListResponse {
        val likeList = memberDomainService.getLikeList(GetLikeList(memberId, request.startId, request.take))

        return MemberFavoriteListResponse.from(
            likeList.list,
            request.nextUrl(likeList.nextStartId)
        )
    }

    private fun uploadProfileImage(image: MultipartFile): List<String> {
        val fileExtension = imageProcessor.getExtension(image.originalFilename)
        val originalImage = imageProcessor.resize(image.inputStream, ImageType.ORIGIN, fileExtension)
        val thumbnailImage = imageProcessor.resize(image.inputStream, ImageType.THUMBNAIL, fileExtension)

        return storageAdapter.uploadImageList(
            listOf(
                FileInfo(file = originalImage, size = ImageType.ORIGIN.size, extension = fileExtension),
                FileInfo(file = thumbnailImage, size = ImageType.THUMBNAIL.size, extension = fileExtension)
            )
        )
    }
}