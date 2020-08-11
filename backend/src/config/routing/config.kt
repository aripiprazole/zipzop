package com.lorenzoog.zipzop.config.routing

import com.lorenzoog.zipzop.AuthenticationException
import com.lorenzoog.zipzop.AuthorizationException
import com.lorenzoog.zipzop.MyLocation
import com.lorenzoog.zipzop.Type
import io.ktor.application.call
import io.ktor.application.install
import io.ktor.features.StatusPages
import io.ktor.http.ContentType
import io.ktor.http.HttpStatusCode
import io.ktor.http.cio.websocket.Frame
import io.ktor.http.cio.websocket.readText
import io.ktor.locations.KtorExperimentalLocationsAPI
import io.ktor.locations.get
import io.ktor.response.respond
import io.ktor.response.respondText
import io.ktor.routing.Routing
import io.ktor.routing.get
import io.ktor.websocket.webSocket

@OptIn(KtorExperimentalLocationsAPI::class)
fun Routing.setup() {
  get("/") {
    call.respondText("HELLO WORLD!", contentType = ContentType.Text.Plain)
  }

  get<MyLocation> {
    call.respondText("Location: name=${it.name}, arg1=${it.arg1}, arg2=${it.arg2}")
  }

  // Register nested routes
  get<Type.Edit> {
    call.respondText("Inside $it")
  }

  get<Type.List> {
    call.respondText("Inside $it")
  }

  install(StatusPages) {
    exception<AuthenticationException> { cause ->
      call.respond(HttpStatusCode.Unauthorized)
    }

    exception<AuthorizationException> { cause ->
      call.respond(HttpStatusCode.Forbidden)
    }
  }

  webSocket("/myws/echo") {
    send(Frame.Text("Hi from server"))
    while (true) {
      val frame = incoming.receive()
      if (frame is Frame.Text) {
        send(Frame.Text("Client said: " + frame.readText()))
      }
    }
  }
}
