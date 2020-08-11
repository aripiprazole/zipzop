package com.lorenzoog.zipzop.config.auth

import com.auth0.jwt.JWT
import com.auth0.jwt.algorithms.Algorithm
import com.lorenzoog.zipzop.entities.User
import com.lorenzoog.zipzop.services.user.UserService
import io.ktor.auth.jwt.JWTPrincipal
import io.ktor.auth.jwt.jwt
import io.ktor.config.ApplicationConfig
import io.ktor.sessions.sessions
import io.ktor.sessions.set
import io.ktor.util.KtorExperimentalAPI
import org.kodein.di.DI
import org.kodein.di.instance
import io.ktor.auth.Authentication.Configuration as AuthConfiguration

data class UserSession(val user: User)

@OptIn(KtorExperimentalAPI::class)
fun AuthConfiguration.setup(di: DI, config: ApplicationConfig) {
  val jwtIssuer = config.property("domain").getString()
  val jwtAudience = config.property("audience").getString()

  jwt {
    realm = config.property("realm").getString()

    val algorithm by di.instance<Algorithm>()
    val userService by di.instance<UserService>()

    verifier(
      JWT.require(algorithm)
        .withAudience(jwtAudience)
        .withIssuer(jwtIssuer)
        .build()
    )

    validate { credentials ->
      if (credentials.payload.audience.contains(jwtAudience)) {
        JWTPrincipal(credentials.payload).also {
          val user = userService.findById(it.payload.subject.toLong())

          sessions.set(UserSession(user))
        }
      } else null
    }
  }
}

