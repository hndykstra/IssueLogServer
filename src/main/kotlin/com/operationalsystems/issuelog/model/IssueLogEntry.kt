package com.operationalsystems.issuelog.model

import com.fasterxml.jackson.annotation.JsonAutoDetect
import com.fasterxml.jackson.annotation.JsonFormat
import com.operationalsystems.issuelog.util.DateSerializer
import kotlinx.serialization.ImplicitReflectionSerializer
import kotlinx.serialization.Serializable
import org.springframework.data.annotation.Id
import org.springframework.data.elasticsearch.annotations.DateFormat
import org.springframework.data.elasticsearch.annotations.Document
import org.springframework.data.elasticsearch.annotations.Field
import org.springframework.data.elasticsearch.annotations.FieldType
import java.util.*

/**
 * Data class for issue log entries. Uses kotlinx serialization
 */
@Serializable
@Document(indexName = "issue_log", type="entry")
data class IssueLogEntry(@Id var id: String? = null,
                         val createdBy: String = "",
                         @Field(type = FieldType.Date, format = DateFormat.custom, pattern = "yyyy-MM-dd'T'HH:mm:ss.SSSZ")
                         @Serializable(with = DateSerializer::class)
                         @get:JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd'T'HH:mm:ss.SSSXX", timezone = "UTC")
                         val createdDate : Date = Date(),
                         val issue: String = "",
                         val analysis: String = "")
