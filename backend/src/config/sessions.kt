package com.lorenzoog.zipzop.config

import com.lorenzoog.zipzop.auth.JwtService
import com.lorenzoog.zipzop.entities.User
import io.ktor.application.Application
import io.ktor.application.ApplicationCall
import io.ktor.application.install
import io.ktor.http.HttpHeaders
import io.ktor.request.header
import io.ktor.response.header
import io.ktor.sessions.SessionProvider
import io.ktor.sessions.SessionTracker
import io.ktor.sessions.SessionTransport
import io.ktor.sessions.Sessions
import org.koin.core.KoinComponent
import org.koin.core.inject

data class UserSession(val user: User)

/**
 * Transport [UserSession] in headers
 */
class UserSessionTransport : SessionTransport {
  override fun clear(call: ApplicationCall) {
    throw RuntimeException("You can not clear a jwt session.")
  }

  override fun receive(call: ApplicationCall): String? {
    return call.request.header(HttpHeaders.Authorization)
  }

  override fun send(call: ApplicationCall, value: String) {
    return call.response.header(HttpHeaders.Authorization, value)
  }
}

/**
 * Retrieve and store [UserSession] with [JwtService]
 */
class UserSessionTracker : SessionTracker<UserSession>, KoinComponent {
  private val jwtService by inject<JwtService>()

  override suspend fun clear(call: ApplicationCall) {
    throw RuntimeException("You can not clear a jwt session.")
  }

  override suspend fun load(call: ApplicationCall, transport: String?) = runCatching {
    UserSession(jwtService.decodeToUser(transport.toString()))
  }.getOrNull()

  override suspend fun store(call: ApplicationCall, value: UserSession): String {
    return jwtService.encode(value.user)
  }

  override fun validate(value: UserSession) = Unit // nothing to do
}

const val USER_SESSION_PROVIDER_NAME = "UserSessionProvider"

fun Application.setupSessions() {
  install(Sessions) {
    register(SessionProvider(
      name = USER_SESSION_PROVIDER_NAME,
      type = UserSession::class,
      transport = UserSessionTransport(),
      tracker = UserSessionTracker()
    ))
  }
}
