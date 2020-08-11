package com.lorenzoog.zipzop.config.logging

import io.ktor.features.CallLogging.Configuration
import io.ktor.request.path
import org.slf4j.event.Level

fun Configuration.setup() {
  level = Level.INFO

  filter { call -> call.request.path().startsWith("/") }
}
