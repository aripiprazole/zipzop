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
import io.ktor.locations.KtorExperimentalLocationsAPI
import io.ktor.locations.Location
import io.ktor.locations.Locations
import io.ktor.routing.Routing
import io.ktor.sessions.Sessions
import io.ktor.util.KtorExperimentalAPI
import io.ktor.websocket.WebSockets
import org.kodein.di.ktor.DIFeature
import org.kodein.di.ktor.di
import java.nio.file.Files
import java.nio.file.Path

fun main(args: Array<String>): Unit = io.ktor.server.netty.EngineMain.main(args)

@Suppress("unused") // Referenced in application.conf
@KtorExperimentalAPI
@kotlin.jvm.JvmOverloads
@OptIn(KtorExperimentalLocationsAPI::class)
fun Application.module(testing: Boolean = false) {
  install(Locations)
  install(ContentNegotiation)
  install(Sessions)

  install(CallLogging) { setup() }

  install(DIFeature) { setup(environment.config) }

  val di = di()

  install(Authentication) { setup(di, environment.config.config("ktor.jwt")) }
  install(WebSockets) { setup() }

  DatabaseInitializer.setupDatabase(environment.config.config("ktor.database"))
  HttpClientInitializer.setupHttpClient()

  install(Routing) { setup() }
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

