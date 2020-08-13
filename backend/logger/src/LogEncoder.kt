package com.lorenzoog.zipzop.logger

import ch.qos.logback.classic.spi.ILoggingEvent
import ch.qos.logback.core.CoreConstants.LINE_SEPARATOR
import ch.qos.logback.core.encoder.EncoderBase
import com.lorenzoog.zipzop.logger.LogColor.Cyan
import com.lorenzoog.zipzop.logger.LogColor.LightCyan
import com.lorenzoog.zipzop.logger.LogColor.Reset
import java.time.LocalDateTime
import java.time.format.DateTimeFormatter

/**
 * https://github.com/nekkan/kodya/blob/development/core/src/main/kotlin/kodya/core/utils/io/logger/LoggingEncoderBase.kt
 */
class LogEncoder(private val dateFormatter: DateTimeFormatter) : EncoderBase<ILoggingEvent>() {
  companion object {
    val format = "[%s] %s$Reset in$Cyan %s$Reset at $LightCyan%s$Reset: %s$Reset$LINE_SEPARATOR"
  }

  override fun headerBytes() = byteArrayOf()
  override fun footerBytes() = byteArrayOf()

  override fun encode(event: ILoggingEvent): ByteArray {
    val time = dateFormatter.format(LocalDateTime.now())
    val level = LogLevel.fromLogbackLevel(event.level)

    val name = event.loggerName
    val thread = event.threadName

    var message = event.message + Reset

    event.throwableProxy?.stackTraceElementProxyArray?.let {
      message += " ${it.joinToString(LINE_SEPARATOR)}"
    }

    return format.format(time, level, name, thread, message).toByteArray()
  }
}
