package com.adevspoon.infrastructure.storage.config

import com.amazonaws.auth.AWSStaticCredentialsProvider
import com.amazonaws.auth.BasicAWSCredentials
import com.amazonaws.client.builder.AwsClientBuilder
import com.amazonaws.services.s3.AmazonS3
import com.amazonaws.services.s3.AmazonS3ClientBuilder
import org.springframework.beans.factory.annotation.Value
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.context.annotation.Profile
import org.testcontainers.containers.localstack.LocalStackContainer
import org.testcontainers.utility.DockerImageName

@Configuration
@ConditionalOnProperty(prefix = "cloud.aws", name = ["s3.bucket"])
class S3Config {
    @Configuration
    @Profile("!local && !test")
    @ConditionalOnProperty(prefix = "cloud.aws", name = ["s3.bucket"])
    class S3ProdConfig {
        @Value("\${cloud.aws.credentials.access-key}")
        private lateinit var accessKey: String

        @Value("\${cloud.aws.credentials.secret-key}")
        private lateinit var secretKey: String

        @Value("\${cloud.aws.region.static}")
        private lateinit var region: String

        @Bean
        fun awsS3Client(): AmazonS3 {
            val credential = BasicAWSCredentials(accessKey, secretKey)
            return AmazonS3ClientBuilder.standard()
                .withRegion(region)
                .withCredentials(AWSStaticCredentialsProvider(credential))
                .build()
        }
    }

    @Configuration
    @Profile("local || test")
    @ConditionalOnProperty(prefix = "cloud.aws", name = ["s3.bucket"])
    class S3LocalConfig {
        private val bucketName = "test-bucket"
        private val localStackImageName = DockerImageName.parse("localstack/localstack")

        @Bean(initMethod = "start", destroyMethod = "stop")
        fun localStackContainer(): LocalStackContainer? {
            return LocalStackContainer(localStackImageName)
                .withServices(LocalStackContainer.Service.S3)
        }

        @Bean
        fun amazonS3(localstack: LocalStackContainer): AmazonS3? {
            val s3 = AmazonS3ClientBuilder
                .standard()
                .withEndpointConfiguration(
                    AwsClientBuilder.EndpointConfiguration(
                        localstack.endpoint.toString(),
                        localstack.region
                    )
                )
                .withCredentials(
                    AWSStaticCredentialsProvider(
                        BasicAWSCredentials(localstack.accessKey, localstack.secretKey)
                    )
                )
                .build()
            s3.createBucket(bucketName)
            return s3
        }
    }
}