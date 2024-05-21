package com.adevspoon.batch.controller

import com.adevspoon.batch.job.JobExecutionFacade
import org.slf4j.LoggerFactory
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

@RestController
@RequestMapping("/events")
class EventProcessController(
    private val jobExecutionFacade: JobExecutionFacade
) {
    private val logger = LoggerFactory.getLogger(this.javaClass)!!
    @PostMapping
    fun processEvents(@RequestBody req: Map<String, Any>): String {
        logger.info("Batch Job 실행 요청")
        jobExecutionFacade.executeJob(req.toString())

        return "Events processed"
    }
}