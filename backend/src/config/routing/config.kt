package com.lorenzoog.zipzop.config.routing

import com.lorenzoog.zipzop.AuthenticationException
import com.lorenzoog.zipzop.AuthorizationException
import com.lorenzoog.zipzop.controllers.sessionController
import io.ktor.application.call
import io.ktor.application.install
import io.ktor.features.StatusPages
import io.ktor.http.HttpStatusCode
import io.ktor.locations.KtorExperimentalLocationsAPI
import io.ktor.response.respond
import io.ktor.routing.Routing

@OptIn(KtorExperimentalLocationsAPI::class)
fun Routing.setup() {
  sessionController()

  install(StatusPages) {
    exception<AuthenticationException> { cause ->
      call.respond(HttpStatusCode.Unauthorized)
    }

    exception<AuthorizationException> { cause ->
      call.respond(HttpStatusCode.Forbidden)
    }
  }
}
