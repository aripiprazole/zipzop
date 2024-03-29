package me.devgabi.zipzop.controllers

import me.devgabi.zipzop.exceptions.AuthenticationException
import me.devgabi.zipzop.auth.JwtService
import me.devgabi.zipzop.auth.password.PasswordEncoder
import me.devgabi.zipzop.dto.auth.LoginResponseDTO
import me.devgabi.zipzop.dto.user.toDto
import me.devgabi.zipzop.services.user.UserService
import me.devgabi.zipzop.utils.current
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

    call.respond(LoginResponseDTO(jwtService.transformUserToJwt(user)))
  }

  get<Session> {
    val session = call.sessions.current()

    call.respond(session.user.toDto())
  }
}
