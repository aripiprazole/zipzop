package com.lorenzoog.zipzop

import io.ktor.application.Application
import io.ktor.application.call
import io.ktor.application.install
import io.ktor.auth.Authentication
import io.ktor.client.HttpClient
import io.ktor.client.engine.cio.CIO
import io.ktor.client.features.HttpTimeout
import io.ktor.client.features.auth.Auth
import io.ktor.client.features.json.GsonSerializer
import io.ktor.client.features.json.JsonFeature
import io.ktor.client.features.logging.LogLevel
import io.ktor.client.features.logging.Logging
import io.ktor.features.CallLogging
import io.ktor.features.ContentNegotiation
import io.ktor.features.StatusPages
import io.ktor.http.ContentType
import io.ktor.http.HttpStatusCode
import io.ktor.http.cio.websocket.Frame
import io.ktor.http.cio.websocket.pingPeriod
import io.ktor.http.cio.websocket.readText
import io.ktor.http.cio.websocket.timeout
import io.ktor.locations.KtorExperimentalLocationsAPI
import io.ktor.locations.Location
import io.ktor.locations.Locations
import io.ktor.locations.get
import io.ktor.request.path
import io.ktor.response.respond
import io.ktor.response.respondText
import io.ktor.routing.get
import io.ktor.routing.routing
import io.ktor.util.KtorExperimentalAPI
import io.ktor.websocket.webSocket
import kotlinx.coroutines.runBlocking
import org.slf4j.event.Level
import java.time.Duration

fun main(args: Array<String>): Unit = io.ktor.server.netty.EngineMain.main(args)

@KtorExperimentalAPI
@OptIn(KtorExperimentalLocationsAPI::class)
@Suppress("unused") // Referenced in application.conf
@kotlin.jvm.JvmOverloads
fun Application.module(testing: Boolean = false) {
  install(Locations) {
  }

  install(CallLogging) {
    level = Level.INFO

    filter { call -> call.request.path().startsWith("/") }
  }

  install(Authentication) {
  }

  install(ContentNegotiation) {
  }

  val client = HttpClient(CIO) {
    install(HttpTimeout) {
    }

    install(Auth) {
    }

    install(JsonFeature) {
      serializer = GsonSerializer()
    }

    install(Logging) {
      level = LogLevel.HEADERS
    }

    // install(UserAgent) { agent = "some user agent" }
  }

  runBlocking {
    // Sample for making a HTTP Client request
    /*
    val message = client.post<JsonSampleClass> {
        url("http://127.0.0.1:8080/path/to/endpoint")
        contentType(ContentType.Application.Json)
        body = JsonSampleClass(hello = "world")
    }
    */
  }

  install(io.ktor.websocket.WebSockets) {
    pingPeriod = Duration.ofSeconds(15)
    timeout = Duration.ofSeconds(15)
    maxFrameSize = Long.MAX_VALUE
    masking = false
  }

  routing {
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
}

@OptIn(KtorExperimentalLocationsAPI::class)
@Location("/location/{name}")
class MyLocation(val name: String, val arg1: Int = 42, val arg2: String = "default")

@OptIn(KtorExperimentalLocationsAPI::class)
@Location("/type/{name}")
data class Type(val name: String) {
  @Location("/edit")
  data class Edit(val type: Type)

  @Location("/list/{page}")
  data class List(val type: Type, val page: Int)
}

class AuthenticationException : RuntimeException()
class AuthorizationException : RuntimeException()

data class JsonSampleClass(val hello: String)

