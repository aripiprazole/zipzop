package com.lorenzoog.zipzop.auth

import com.auth0.jwt.JWT
import com.auth0.jwt.algorithms.Algorithm
import com.auth0.jwt.exceptions.JWTDecodeException
import com.lorenzoog.zipzop.AuthorizationException
import com.lorenzoog.zipzop.entities.User
import com.lorenzoog.zipzop.services.user.UserService
import io.ktor.config.ApplicationConfig
import io.ktor.util.KtorExperimentalAPI
import org.koin.core.KoinComponent
import org.koin.core.inject
import java.time.Instant
import java.util.*

@OptIn(KtorExperimentalAPI::class)
class JwtService(private val config: ApplicationConfig) : KoinComponent {
  private val algorithm by inject<Algorithm>()
  private val userService by inject<UserService>()

  fun transformUserToJwt(user: User): String =
    JWT.create()
      .withAudience(config.property("audience").getString())
      .withIssuer(config.property("issuer").getString())
      .withIssuedAt(Date.from(Instant.now()))
      .withSubject(user.id.toString())
      .sign(algorithm)

  suspend fun transformJwtToUser(jwt: String): User = try {
    userService.findById(JWT.decode(jwt).subject.toLong())
  } catch (exception: JWTDecodeException) {
    throw AuthorizationException()
  }
}
