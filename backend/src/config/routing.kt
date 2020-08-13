package com.lorenzoog.zipzop.config

import com.lorenzoog.zipzop.exceptions.AuthenticationException
import com.lorenzoog.zipzop.exceptions.AuthorizationException
import com.lorenzoog.zipzop.controllers.sessionController
import com.lorenzoog.zipzop.dto.exceptions.ExceptionDTO
import com.lorenzoog.zipzop.exceptions.EntityNotFoundException
import com.lorenzoog.zipzop.exceptions.UniqueFieldViolationException
import io.ktor.application.call
import io.ktor.application.install
import io.ktor.features.StatusPages
import io.ktor.http.HttpStatusCode
import io.ktor.locations.KtorExperimentalLocationsAPI
import io.ktor.response.respond
import io.ktor.routing.Routing
import kotlinx.serialization.SerializationException
import kotlinx.serialization.json.JsonDecodingException

@OptIn(KtorExperimentalLocationsAPI::class)
fun Routing.setupMainRouter() {
  sessionController()

  install(StatusPages) {
    exception<AuthenticationException> {
      call.respond(HttpStatusCode.Forbidden, ExceptionDTO("Invalid credentials."))
    }

    exception<AuthorizationException> {
      call.respond(HttpStatusCode.Forbidden, ExceptionDTO("You are not allowed to access."))
    }

    exception<JsonDecodingException> {
      call.respond(HttpStatusCode.BadRequest, ExceptionDTO("Invalid json!"))
    }

    exception<SerializationException> { cause ->
      call.respond(HttpStatusCode.BadRequest, ExceptionDTO(cause.message.toString()))
    }

    exception<EntityNotFoundException> { cause ->
      call.respond(HttpStatusCode.NotFound, ExceptionDTO(cause.message))
    }

    exception<UniqueFieldViolationException> { cause ->
      call.respond(HttpStatusCode.Conflict, ExceptionDTO(cause.message.toString()))
    }
  }
}
