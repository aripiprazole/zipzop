package com.lorenzoog.zipzop.config

import com.auth0.jwt.JWT
import com.auth0.jwt.algorithms.Algorithm
import com.lorenzoog.zipzop.entities.User
import com.lorenzoog.zipzop.services.user.UserService
import io.ktor.application.Application
import io.ktor.auth.authentication
import io.ktor.auth.jwt.JWTPrincipal
import io.ktor.auth.jwt.jwt
import io.ktor.config.ApplicationConfig
import io.ktor.sessions.sessions
import io.ktor.sessions.set
import io.ktor.util.KtorExperimentalAPI
import org.koin.ktor.ext.inject

@OptIn(KtorExperimentalAPI::class)
fun Application.setupAuthentication(config: ApplicationConfig) {
  val jwtIssuer = config.property("domain").getString()
  val jwtAudience = config.property("audience").getString()

  val algorithm by inject<Algorithm>()
  val userService by inject<UserService>()

  authentication {
    jwt {
      realm = config.property("realm").getString()

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
}

