package com.s3uploadapi.s3uploader.repository

import com.s3uploadapi.s3uploader.model.ActionLog
import org.springframework.data.mongodb.core.ReactiveMongoTemplate
import org.springframework.data.mongodb.core.find
import org.springframework.data.mongodb.core.findById
import org.springframework.data.mongodb.core.query.Query
import org.springframework.data.mongodb.core.query.isEqualTo
import org.springframework.data.mongodb.core.query.Criteria.where
import org.springframework.data.mongodb.core.remove
import org.springframework.stereotype.Repository
import reactor.core.publisher.Mono
import reactor.kotlin.core.publisher.toMono
import javax.annotation.PostConstruct
import javax.swing.Action

@Repository
class ActionLogRepository(private val template: ReactiveMongoTemplate) {
    companion object {
        val initialActionLogs = listOf(
            ActionLog(1, "qwer@s3.com"),
            ActionLog(2, "asdf@s3.com"),
            ActionLog(3, "zxcv@s3.com"),
        )
    }

    @PostConstruct
    fun initializeRepository() =
        initialActionLogs.map(ActionLog::toMono)
            .map(this::create)
            .map(Mono<ActionLog>::subscribe)

    fun create(actionLog: Mono<ActionLog>) = template.save(actionLog)
    fun findById(id: Int) = template.findById<ActionLog>(id)
    fun deleteById(id: Int) = template.remove<ActionLog>(
        Query(where("_id").isEqualTo(id))
    )
    fun findActionLogs(date: String) = template.find<ActionLog>(
        Query(where("analyedAt").regex(".*$date.*", "i"))
    )
}