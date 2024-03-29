package me.devgabi.zipzop.logger

import ch.qos.logback.classic.Level

/**
 * https://github.com/nekkan/kodya/blob/development/core/src/main/kotlin/kodya/core/utils/io/logger/LoggingLevel.kt
 */
enum class LogLevel(private val level: String, private val emoji: String, private val color: String) {
  Info("info", "\uD83D\uDC81", LogColor.LightYellow),
  Warn("warn", "⚠", LogColor.Yellow),
  Error("error", "❌", LogColor.Red),
  Debug("debug", "\uD83D\uDE80", LogColor.Cyan),
  Trace("trace", "\uD83D\uDC63", LogColor.Blue),
  All("all", "\uD83D\uDCD9", Trace.color),
  None("", "", "");

  override fun toString() = "$color$emoji $level"

  companion object {
    fun fromLogbackLevel(level: Level): LogLevel {
      val levelStr = level.levelStr.trim().toLowerCase()

      return values()
        .find {
          it.level == levelStr
        } ?: None
    }
  }
}
