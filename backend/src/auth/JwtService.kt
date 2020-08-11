package com.lorenzoog.zipzop.auth

import com.auth0.jwt.JWT
import com.auth0.jwt.algorithms.Algorithm
import com.lorenzoog.zipzop.entities.User
import com.lorenzoog.zipzop.services.user.UserService
import io.ktor.config.ApplicationConfig
import io.ktor.util.KtorExperimentalAPI
import org.kodein.di.DI
import org.kodein.di.DIAware
import org.kodein.di.instance
import java.time.Instant
import java.util.*

@OptIn(KtorExperimentalAPI::class)
class JwtService(
  private val config: ApplicationConfig,
  override val di: DI
) : DIAware {
  private val algorithm by di.instance<Algorithm>()
  private val userService by di.instance<UserService>()

  fun encode(user: User): String =
    JWT.create()
      .withAudience(config.property("audience").getString())
      .withIssuer(config.property("issuer").getString())
      .withIssuedAt(Date.from(Instant.now()))
      .sign(algorithm)
}
