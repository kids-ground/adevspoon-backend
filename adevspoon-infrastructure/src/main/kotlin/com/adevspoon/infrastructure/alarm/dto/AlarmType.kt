package com.adevspoon.infrastructure.alarm.dto

enum class AlarmType(
    val title: String,
) {
    CHECK("체크 ✔️"),
    REPORT("신고 접수 ⚠️"),
    BATCH_COMPLETE("Batch 성공 ✅"),
    BATCH_ERROR("Batch 처리 에러 🚨"),
}