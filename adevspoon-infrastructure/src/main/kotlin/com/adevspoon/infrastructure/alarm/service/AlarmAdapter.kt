package com.adevspoon.infrastructure.alarm.service

import com.adevspoon.infrastructure.alarm.dto.AlarmType

interface AlarmAdapter {
    fun sendAlarm(type: AlarmType, info: Map<String, Any>)
}