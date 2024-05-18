package com.adevspoon.infrastructure.notification.service

import com.adevspoon.infrastructure.notification.dto.GroupNotificationInfo
import com.adevspoon.infrastructure.notification.dto.NotificationInfo
import com.adevspoon.infrastructure.notification.dto.NotificationResponse

interface PushNotificationAdapter {
    fun sendMessageSync(info: NotificationInfo): NotificationResponse
    fun sendMessageSync(info: GroupNotificationInfo): NotificationResponse
}