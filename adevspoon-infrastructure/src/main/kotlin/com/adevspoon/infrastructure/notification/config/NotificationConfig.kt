package com.adevspoon.infrastructure.notification.config

import com.adevspoon.infrastructure.notification.dto.GroupNotificationInfo
import com.adevspoon.infrastructure.notification.dto.NotificationInfo
import com.adevspoon.infrastructure.notification.dto.NotificationResponse
import com.adevspoon.infrastructure.notification.service.FCMNotificationAdapter
import com.adevspoon.infrastructure.notification.service.PushNotificationAdapter
import com.google.firebase.messaging.FirebaseMessaging
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.context.annotation.Profile

@Configuration
class NotificationConfig {
    @Bean
    @Profile("prod || dev")
    fun pushNotificationAdapter(messaging: FirebaseMessaging): PushNotificationAdapter {
        return FCMNotificationAdapter(messaging)
    }

    @Bean
    @Profile("!prod && !dev")
    fun localPushNotificationAdapter(): PushNotificationAdapter {
        return object: PushNotificationAdapter {
            override fun sendMessageSync(info: NotificationInfo): NotificationResponse {
                return NotificationResponse(1, 0, emptyList())
            }

            override fun sendMessageSync(info: GroupNotificationInfo): NotificationResponse {
                return NotificationResponse(info.tokens.size, 0, emptyList())
            }
        }
    }
}