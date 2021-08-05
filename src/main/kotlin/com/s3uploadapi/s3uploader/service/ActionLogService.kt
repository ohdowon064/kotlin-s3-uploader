package com.s3uploadapi.s3uploader.service

import com.s3uploadapi.s3uploader.model.ActionLog
import reactor.core.publisher.Flux
import reactor.core.publisher.Mono

interface ActionLogService {

    fun getActionLog(id: Int): Mono<ActionLog>
    fun createActionLog(actionLog: Mono<ActionLog>): Mono<ActionLog>
    fun deleteActionLog(id: Int): Mono<Boolean>
    fun searchActionLogs(date: String): Flux<ActionLog>
}