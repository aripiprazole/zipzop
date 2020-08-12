package com.lorenzoog.zipzop.config

import com.lorenzoog.zipzop.AuthenticationException
import com.lorenzoog.zipzop.AuthorizationException
import com.lorenzoog.zipzop.controllers.sessionController
import com.lorenzoog.zipzop.dto.exceptions.JsonDecodingExceptionDTO
import io.ktor.application.call
import io.ktor.application.install
import io.ktor.features.StatusPages
import io.ktor.http.HttpStatusCode
import io.ktor.locations.KtorExperimentalLocationsAPI
import io.ktor.response.respond
import io.ktor.routing.Routing
import kotlinx.serialization.json.JsonDecodingException

@OptIn(KtorExperimentalLocationsAPI::class)
fun Routing.setupMainRouter() {
  sessionController()

  install(StatusPages) {
    exception<AuthenticationException> {
      call.respond("ALAMEUPAU NO SEU N AUTENTICADO HAHAHAHHAHAHA")
    }

    exception<AuthorizationException> {
      call.respond(HttpStatusCode.Forbidden)
    }

    exception<JsonDecodingException> {
      call.respond(HttpStatusCode.BadRequest, JsonDecodingExceptionDTO("Invalid json!"))
    }
  }
}
