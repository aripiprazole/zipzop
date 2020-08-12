package com.lorenzoog.zipzop.config

import com.lorenzoog.zipzop.AuthenticationException
import com.lorenzoog.zipzop.AuthorizationException
import com.lorenzoog.zipzop.controllers.sessionController
import com.lorenzoog.zipzop.dto.exceptions.ExceptionDTO
import io.ktor.application.call
import io.ktor.application.install
import io.ktor.features.StatusPages
import io.ktor.http.HttpStatusCode
import io.ktor.locations.KtorExperimentalLocationsAPI
import io.ktor.response.respond
import io.ktor.routing.Routing
import kotlinx.serialization.MissingFieldException
import kotlinx.serialization.SerializationException
import kotlinx.serialization.json.JsonDecodingException
import org.jetbrains.exposed.dao.exceptions.EntityNotFoundException

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

    exception<EntityNotFoundException> {
      call.respond(HttpStatusCode.NotFound, ExceptionDTO("Not found the requested entity"))
    }
  }
}
