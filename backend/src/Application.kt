package com.lorenzoog.zipzop

import com.lorenzoog.zipzop.config.auth.setup
import com.lorenzoog.zipzop.config.database.DatabaseInitializer
import com.lorenzoog.zipzop.config.httpclient.HttpClientInitializer
import com.lorenzoog.zipzop.config.kodein.setup
import com.lorenzoog.zipzop.config.logging.setup
import com.lorenzoog.zipzop.config.routing.setup
import com.lorenzoog.zipzop.config.websockets.setup
import io.ktor.application.Application
import io.ktor.application.install
import io.ktor.auth.Authentication
import io.ktor.features.CallLogging
import io.ktor.features.ContentNegotiation
import io.ktor.http.ContentType
import io.ktor.locations.KtorExperimentalLocationsAPI
import io.ktor.locations.Locations
import io.ktor.routing.Routing
import io.ktor.serialization.json
import io.ktor.sessions.Sessions
import io.ktor.util.KtorExperimentalAPI
import io.ktor.websocket.WebSockets
import kotlinx.serialization.json.Json
import kotlinx.serialization.json.JsonConfiguration
import org.kodein.di.ktor.DIFeature
import org.kodein.di.ktor.di

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
  install(CallLogging) { setup() }

  install(DIFeature) { setup(environment.config) }

  val di = di()

  install(Authentication) { setup(di, environment.config.config("ktor.jwt")) }
  install(WebSockets) { setup() }

  DatabaseInitializer.setupDatabase(environment.config.config("ktor.database"))
  HttpClientInitializer.setupHttpClient()

  install(Routing) { setup() }
}

class AuthenticationException : RuntimeException()
class AuthorizationException : RuntimeException()

data class JsonSampleClass(val hello: String)

