package com.operationalsystems.issuelog

import com.operationalsystems.issuelog.util.DateSerializer
import org.junit.Assert
import org.junit.Test
import org.junit.runner.RunWith
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.test.context.junit4.SpringRunner
import java.util.*

/**
 * Test to verify kotlinx serializer methods.
 */
@RunWith(SpringRunner::class)
@SpringBootTest
class DateSerializerTest {
  @Test
  fun testSerialize() {
    val input = Date()
    val formatted = DateSerializer.serializeRaw(input)

    DateSerializer.deserializeRaw(formatted)
    // assert no exceptions
  }

  @Test
  fun testDeserialize() {
    val input = "2018-12-27T12:00:00.000Z"
    val parsed = DateSerializer.deserializeRaw(input)

    val output = DateSerializer.serializeRaw(parsed)
    Assert.assertEquals("Failed deserialize", "2018-12-27T12:00:00.000Z", output)
  }
}