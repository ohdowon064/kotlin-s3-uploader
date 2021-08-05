package com.s3uploadapi.s3uploader.model

import org.springframework.data.annotation.Id
import org.springframework.data.mongodb.core.mapping.Document
import java.time.LocalDate
import java.time.LocalDateTime

@Document(collection = "Actions")
data class ActionLog(
    @Id
    var id: Int? = null,
    var s3Url: String? = null,
    var analyzedAt: LocalDate = LocalDate.now(),
    var createdAt: LocalDateTime = LocalDateTime.now(),
)