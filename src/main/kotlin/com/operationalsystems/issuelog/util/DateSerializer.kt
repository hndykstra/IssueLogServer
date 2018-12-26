package com.operationalsystems.issuelog.util

import kotlinx.serialization.*
import kotlinx.serialization.internal.StringDescriptor
import java.text.SimpleDateFormat
import java.util.Date

@Serializer(forClass = Date::class)
object DateSerializer : KSerializer<Date> {
  private val dateFormat = SimpleDateFormat("yyyy-MM-dd'T'hh:mm:ss.SSS'Z'")

  override val descriptor: SerialDescriptor = StringDescriptor.withName("WithISO8601UTC")

  @ImplicitReflectionSerializer
  override fun serialize(output: Encoder, obj: Date) {
    output.encode(dateFormat.format(obj))
  }

  @ImplicitReflectionSerializer
  override fun deserialize(input: Decoder): Date {
    return dateFormat.parse(input.decode())
  }

  fun serializeRaw(obj: Date) : String {
    return dateFormat.format(obj)
  }

  fun deserializeRaw(input: String) : Date {
    return dateFormat.parse(input)
  }
}