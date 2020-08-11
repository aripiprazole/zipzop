package com.lorenzoog.zipzop

import com.lorenzoog.zipzop.config.setup
import com.lorenzoog.zipzop.config.DatabaseInitializer
import com.lorenzoog.zipzop.config.HttpClientInitializer
import com.lorenzoog.zipzop.config.kodein.setup
import io.ktor.application.Application
import io.ktor.application.install
import io.ktor.auth.Authentication
import io.ktor.features.CallLogging
import io.ktor.features.ContentNegotiation
import io.ktor.http.cio.websocket.pingPeriod
import io.ktor.http.cio.websocket.timeout
import io.ktor.locations.KtorExperimentalLocationsAPI
import io.ktor.locations.Locations
import io.ktor.request.path
import io.ktor.routing.Routing
import io.ktor.serialization.json
import io.ktor.sessions.Sessions
import io.ktor.util.KtorExperimentalAPI
import io.ktor.websocket.WebSockets
import kotlinx.serialization.json.Json
import kotlinx.serialization.json.JsonConfiguration
import org.kodein.di.ktor.DIFeature
import org.kodein.di.ktor.di
import org.slf4j.event.Level
import java.time.Duration

fun main(args: Array<String>): Unit = io.ktor.server.netty.EngineMain.main(args)

@Suppress("unused") // Referenced in application.conf
@KtorExperimentalAPI
@kotlin.jvm.JvmOverloads
@OptIn(KtorExperimentalLocationsAPI::class)
fun Application.module(testing: Boolean = false) {
  install(Locations)
  install(Sessions)
  install(ContentNegotiation) {
    json(json = Json(JsonConfiguration.Stable))
  }
  install(CallLogging) {
    level = Level.INFO

    filter { call -> call.request.path().startsWith("/") }
  }
  install(DIFeature) {
    setup(environment.config)
  }

  val di = di()

  install(Authentication) {
    setup(di, environment.config.config("ktor.jwt"))
  }

  install(WebSockets) {
    pingPeriod = Duration.ofSeconds(15)
    timeout = Duration.ofSeconds(15)
    maxFrameSize = Long.MAX_VALUE
    masking = false
  }

  DatabaseInitializer.setupDatabase(environment.config.config("ktor.database"))
  HttpClientInitializer.setupHttpClient()

  install(Routing) { setup() }
}

class AuthenticationException : RuntimeException()
class AuthorizationException : RuntimeException()

data class JsonSampleClass(val hello: String)

