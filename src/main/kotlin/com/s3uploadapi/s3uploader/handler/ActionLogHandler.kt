package com.s3uploadapi.s3uploader.handler

import com.s3uploadapi.s3uploader.model.ActionLog
import com.s3uploadapi.s3uploader.service.ActionLogService
import org.springframework.http.HttpStatus
import org.springframework.http.codec.multipart.FilePart
import org.springframework.http.codec.multipart.Part
import org.springframework.stereotype.Component
import org.springframework.web.reactive.function.BodyExtractors
import org.springframework.web.reactive.function.BodyInserters.fromObject
import org.springframework.web.reactive.function.server.ServerRequest
import org.springframework.web.reactive.function.server.ServerResponse
import org.springframework.web.reactive.function.server.bodyToMono
import org.springframework.web.server.ServerWebExchange
import reactor.core.publisher.Mono
import java.io.File
import java.net.URI
import kotlin.reflect.full.memberProperties

@Component
class ActionLogHandler(val actionLogService: ActionLogService) {

    fun get(serverRequest: ServerRequest) =
        actionLogService.getActionLog(serverRequest.pathVariable("id").toInt())
            .flatMap { ServerResponse.ok().body(fromObject(it)) }
            .switchIfEmpty(ServerResponse.status(HttpStatus.NOT_FOUND).build())

    fun create(serverRequest: ServerRequest): Mono<ServerResponse> {
        serverRequest.body(BodyExtractors.toMultipartData()).flatMap { parts->
            val map: Map<String, Part> = parts.toSingleValueMap()
            val filePart: FilePart = map["file"]!! as FilePart
            val fileName = filePart.filename()
            print(fileName)
            filePart.transferTo(File("/tmp/$fileName"))
        }

        return actionLogService.createActionLog(serverRequest.bodyToMono()).flatMap {
            ServerResponse.created(URI.create("/action-log/${it.id}")).build()
        }
    }

    fun delete(serverRequest: ServerRequest) =
        actionLogService.deleteActionLog(serverRequest.pathVariable("id").toInt())
            .flatMap { ServerResponse.ok().build() }
            .switchIfEmpty(ServerResponse.status(HttpStatus.NOT_FOUND).build())

    fun search(serverRequest: ServerRequest) =
        ServerResponse.ok().body(
            actionLogService.searchActionLogs(
                serverRequest.queryParam("date").orElse("")
            ), ActionLog::class.java
        )
}