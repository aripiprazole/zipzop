package me.devgabi.zipzop

import me.devgabi.zipzop.config.*
import me.devgabi.zipzop.config.di.authModule
import me.devgabi.zipzop.config.di.mainModule
import io.ktor.application.Application
import io.ktor.application.install
import io.ktor.features.CallLogging
import io.ktor.features.ContentNegotiation
import io.ktor.http.cio.websocket.pingPeriod
import io.ktor.http.cio.websocket.timeout
import io.ktor.locations.KtorExperimentalLocationsAPI
import io.ktor.locations.Locations
import io.ktor.request.path
import io.ktor.routing.routing
import io.ktor.serialization.json
import io.ktor.util.KtorExperimentalAPI
import io.ktor.websocket.WebSockets
import kotlinx.serialization.json.Json
import kotlinx.serialization.json.JsonConfiguration
import org.koin.ktor.ext.koin
import org.slf4j.event.Level
import java.time.Duration

fun main(args: Array<String>): Unit = io.ktor.server.netty.EngineMain.main(args)

/**
 * Common Json instance for the project with [JsonConfiguration.Stable]
 */
val Json = Json(JsonConfiguration.Stable)

@Suppress("unused") // Referenced in application.conf
@KtorExperimentalAPI
@OptIn(KtorExperimentalLocationsAPI::class)
fun Application.module() {
  // Inject dependencies to the code
  koin {
    modules(
      mainModule(),
      authModule(environment.config.config("jwt"))
    )
  }

  // Setup common services
  setupDatabase(environment.config.config("database"))
  setupAuthentication(environment.config.config("jwt"))
  setupSessions()

  // Install [Locations] Ktor Experimental Locations API to make easier to read the code
  install(Locations)

  // Install [ContentNegotiation] with local [Json]
  install(ContentNegotiation) {
    json(json = me.devgabi.zipzop.Json)
  }

  // Install [CallLogging] to log the application requests
  install(CallLogging) {
    level = Level.INFO

    filter { call -> call.request.path().startsWith("/") }
  }

  // Install [WebSockets] connections to work the messaging system
  install(WebSockets) {
    pingPeriod = Duration.ofSeconds(15)
    timeout = Duration.ofSeconds(15)
    maxFrameSize = Long.MAX_VALUE
    masking = false
  }

  // Setup main router
  routing {
    setupMainRouter()
  }
}
