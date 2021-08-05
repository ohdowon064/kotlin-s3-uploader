package com.s3uploadapi.s3uploader.router

import com.s3uploadapi.s3uploader.handler.ActionLogHandler
import org.springframework.context.annotation.Bean
import org.springframework.stereotype.Component
import org.springframework.web.reactive.function.server.router

@Component
class ActionLogRouter(private val actionLogHandler: ActionLogHandler) {

    @Bean
    fun actionLogRoutes() = router {
        "/action-log".nest {
            GET("/{id}", actionLogHandler::get)
            GET(actionLogHandler::search)
            POST(actionLogHandler::create)
            DELETE("/{id}", actionLogHandler::delete)
        }
    }
}