package com.lorenzoog.zipzop.controllers

import com.lorenzoog.zipzop.Json
import com.lorenzoog.zipzop.dto.messages.TextMessagePacket
import com.lorenzoog.zipzop.services.user.UserService
import com.lorenzoog.zipzop.utils.current
import io.ktor.http.cio.websocket.Frame
import io.ktor.http.cio.websocket.readText
import io.ktor.routing.Routing
import io.ktor.sessions.sessions
import io.ktor.websocket.WebSocketServerSession
import io.ktor.websocket.webSocket
import kotlinx.coroutines.sync.Mutex
import kotlinx.coroutines.sync.withLock
import org.koin.ktor.ext.inject

fun Routing.messageController() {
  val connections = mutableMapOf<Long, WebSocketServerSession>()
  val mutex = Mutex()

  webSocket("/messages") {
    val user = call.sessions.current().user

    mutex.withLock { connections.putIfAbsent(user.id.value, this) }

    try {
      while (true) {
        when (val frame = incoming.receive()) {
          is Frame.Text -> {
            val packet = Json.parse(TextMessagePacket.serializer(), frame.readText())

            val target = connections[packet.targetId]

            application.environment.log.debug("Sender: $user; Target: $target; ")

            target!!.outgoing.send(Frame.Text(packet.message))
          }
          else -> {
            error("No handler for frame $frame")
          }
        }
      }
    } finally {
      mutex.withLock {
        connections.remove(user.id.value)
      }
    }
  }
}
