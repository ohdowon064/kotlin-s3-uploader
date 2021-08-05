package com.s3uploadapi.s3uploader.service

import com.s3uploadapi.s3uploader.model.ActionLog
import com.s3uploadapi.s3uploader.repository.ActionLogRepository
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Service
import reactor.core.publisher.Flux
import reactor.core.publisher.Mono
import reactor.kotlin.core.publisher.toMono

@Service
class ActionLogServiceImpl : ActionLogService {

    @Autowired
    lateinit var actionLogRepository: ActionLogRepository

    override fun getActionLog(id: Int) = actionLogRepository.findById(id)

    override fun createActionLog(actionLog: Mono<ActionLog>): Mono<ActionLog> =
        actionLogRepository.create(actionLog)

    override fun deleteActionLog(id: Int): Mono<Boolean> {
        return actionLogRepository.deleteById(id).map {
            it.deletedCount > 0
        }
    }

    override fun searchActionLogs(date: String): Flux<ActionLog> {
        return actionLogRepository.findActionLogs(date)
    }
}