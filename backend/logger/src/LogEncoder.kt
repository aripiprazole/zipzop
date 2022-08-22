package me.devgabi.zipzop.logger

import ch.qos.logback.classic.spi.ILoggingEvent
import ch.qos.logback.core.CoreConstants.LINE_SEPARATOR
import ch.qos.logback.core.encoder.EncoderBase
import me.devgabi.zipzop.logger.LogColor.Cyan
import me.devgabi.zipzop.logger.LogColor.LightCyan
import me.devgabi.zipzop.logger.LogColor.Red
import me.devgabi.zipzop.logger.LogColor.Reset
import java.time.LocalDateTime
import java.time.format.DateTimeFormatter

/**
 * https://github.com/nekkan/kodya/blob/development/core/src/main/kotlin/kodya/core/utils/io/logger/LoggingEncoderBase.kt
 */
class LogEncoder(private val dateFormatter: DateTimeFormatter) : EncoderBase<ILoggingEvent>() {
  companion object {
    val format = "[%s] %s$Reset in$Cyan %s$Reset at $Cyan%s$Reset: %s$Reset$LINE_SEPARATOR"
  }

  override fun headerBytes() = byteArrayOf()
  override fun footerBytes() = byteArrayOf()

  override fun encode(event: ILoggingEvent): ByteArray {
    val time = dateFormatter.format(LocalDateTime.now())
    val level = LogLevel.fromLogbackLevel(event.level)

    val name = event.loggerName
    val thread = event.threadName

    val message = if (event.throwableProxy == null)
      event.message + Reset
    else event.throwableProxy!!.stackTraceElementProxyArray?.let {
      val cause = event.throwableProxy

      val causeMessage = "${cause?.className}: ${cause?.message}"

      listOf(LINE_SEPARATOR + Red + causeMessage, *it)
        .joinToString(separator = "") { element ->
          "\t$Red$element$LINE_SEPARATOR"
        }
    }

    return format.format(time, level, name, thread, message).toByteArray()
  }
}
