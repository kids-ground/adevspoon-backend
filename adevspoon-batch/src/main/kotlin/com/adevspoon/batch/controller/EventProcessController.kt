package com.adevspoon.batch.controller

import com.adevspoon.batch.job.JobExecutionFacade
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

@RestController
@RequestMapping("/events")
class EventProcessController(
    private val jobExecutionFacade: JobExecutionFacade
) {
    @PostMapping
    fun processEvents(@RequestBody req: Map<String, Any>): ResponseEntity<String> {
        jobExecutionFacade.executeJob()
        return ResponseEntity.ok("Events processed")
    }
}