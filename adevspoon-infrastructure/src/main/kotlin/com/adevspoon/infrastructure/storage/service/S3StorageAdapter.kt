package com.adevspoon.infrastructure.storage.service

import com.adevspoon.infrastructure.storage.config.*
import com.adevspoon.infrastructure.storage.dto.FileInfo
import com.adevspoon.infrastructure.storage.exception.StorageS3UploadException
import com.adevspoon.infrastructure.storage.util.getImageRootPath
import com.adevspoon.infrastructure.storage.util.getImageUploadPath
import com.amazonaws.services.s3.AmazonS3
import com.amazonaws.services.s3.model.ObjectMetadata
import org.slf4j.LoggerFactory
import org.springframework.beans.factory.annotation.Value
import java.util.*

class S3StorageAdapter(
    private val s3Client: AmazonS3
): StorageAdapter {
    private val logger = LoggerFactory.getLogger(this.javaClass)!!

    @Value("\${cloud.aws.s3.bucket}")
    lateinit var s3Bucket: String

    @Value("\${cloud.aws.region.static}")
    lateinit var s3Region: String

    override fun uploadImage(file: FileInfo): String {
        val imageRootPath = file.rootPath ?: getImageRootPath()
        val imagePath = getImageUploadPath(imageRootPath, file.size, file.extension.value)

        try {
            s3Client.putObject(
                s3Bucket,
                imagePath,
                file.file,
                ObjectMetadata()
            )
            logger.info("S3에 이미지 업로드 완료: ${getImageUrl(imagePath)}")
            return getImageUrl(imagePath)
        } catch (e: Exception) {
            throw StorageS3UploadException(e.message)
        }
    }

    override fun uploadImageList(files: List<FileInfo>): List<String> {
        val imageRootPath = getImageRootPath()
        return files.map { file -> file.copy(rootPath = imageRootPath) }
            .map { file -> uploadImage(file) }
            .toList()
    }

    private fun getImageUrl(path: String) =
        "https://$s3Bucket.s3.$s3Region.amazonaws.com/${path}"
}