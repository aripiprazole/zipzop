package com.lorenzoog.zipzop.config

import io.ktor.http.cio.websocket.pingPeriod
import io.ktor.http.cio.websocket.timeout
import io.ktor.websocket.WebSockets.WebSocketOptions
import java.time.Duration

fun WebSocketOptions.setup() {
  pingPeriod = Duration.ofSeconds(15)
  timeout = Duration.ofSeconds(15)
  maxFrameSize = Long.MAX_VALUE
  masking = false
}
