package com.lorenzoog.zipzop.controllers

import com.lorenzoog.zipzop.AuthenticationException
import com.lorenzoog.zipzop.auth.JwtService
import com.lorenzoog.zipzop.config.auth.password.PasswordEncoder
import com.lorenzoog.zipzop.dto.auth.LoginResponseDTO
import com.lorenzoog.zipzop.dto.user.toDto
import com.lorenzoog.zipzop.services.user.UserService
import com.lorenzoog.zipzop.utils.current
import io.ktor.application.call
import io.ktor.locations.KtorExperimentalLocationsAPI
import io.ktor.locations.Location
import io.ktor.locations.get
import io.ktor.locations.post
import io.ktor.request.receive
import io.ktor.response.respond
import io.ktor.routing.Routing
import io.ktor.sessions.sessions
import kotlinx.serialization.Serializable
import org.koin.ktor.ext.inject

@OptIn(KtorExperimentalLocationsAPI::class)
@Location("/login")
class Login {

  @Serializable
  data class Body(val username: String, val password: String)

}

@OptIn(KtorExperimentalLocationsAPI::class)
@Location("/session")
class Session

@OptIn(KtorExperimentalLocationsAPI::class)
fun Routing.sessionController() {
  val userService by inject<UserService>()
  val passwordEncoder by inject<PasswordEncoder>()
  val jwtService by inject<JwtService>()

  post<Login> {
    val credentials = call.receive<Login.Body>()

    val user = userService.findByUsername(credentials.username)

    if (!passwordEncoder.matches(credentials.password, user.password))
      throw AuthenticationException()

    call.respond(LoginResponseDTO(jwtService.encode(user)))
  }

  get<Session> {
    val session = call.sessions.current()

    call.respond(session.user.toDto())
  }
}
