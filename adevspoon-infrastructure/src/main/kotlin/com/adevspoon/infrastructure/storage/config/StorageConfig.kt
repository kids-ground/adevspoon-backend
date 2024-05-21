package com.adevspoon.infrastructure.storage.config


import com.adevspoon.infrastructure.storage.dto.FileInfo
import com.adevspoon.infrastructure.storage.service.S3StorageAdapter
import com.adevspoon.infrastructure.storage.service.StorageAdapter
import com.amazonaws.services.s3.AmazonS3
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration


@Configuration
class StorageConfig {

    @Bean
    @ConditionalOnMissingBean(StorageAdapter::class)
    @ConditionalOnProperty(prefix = "cloud.aws", name = ["s3.bucket"])
    fun s3Storage(s3Client: AmazonS3): StorageAdapter {
        return S3StorageAdapter(s3Client)
    }

    @Bean
    @ConditionalOnMissingBean(StorageAdapter::class)
    fun dummyStorage(): StorageAdapter {
        return object: StorageAdapter {
            override fun uploadImage(file: FileInfo): String {
                return ""
            }
            override fun uploadImageList(files: List<FileInfo>): List<String> {
                return listOf("")
            }
        }
    }
}