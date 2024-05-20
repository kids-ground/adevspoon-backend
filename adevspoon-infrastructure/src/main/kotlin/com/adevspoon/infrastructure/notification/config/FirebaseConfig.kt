package com.adevspoon.infrastructure.notification.config

import com.google.auth.oauth2.GoogleCredentials
import com.google.firebase.FirebaseApp
import com.google.firebase.FirebaseOptions
import com.google.firebase.messaging.FirebaseMessaging
import org.springframework.beans.factory.annotation.Value
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.core.io.ClassPathResource


@Configuration
@ConditionalOnProperty(prefix = "firebase", name = ["key-path"])
class FirebaseConfig {
    @Value("\${firebase.key-path}")
    private lateinit var keyPath: String

    @Bean
    fun firebaseMessaging(app: FirebaseApp): FirebaseMessaging {
        return FirebaseMessaging.getInstance(initializeFirebase())
    }

    @Bean
    fun initializeFirebase(): FirebaseApp {
        val adminKey = ClassPathResource(keyPath)
        val options = FirebaseOptions.builder()
            .setCredentials(GoogleCredentials.fromStream(adminKey.inputStream))
            .build()

        return FirebaseApp.initializeApp(options)
    }
}