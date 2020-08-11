package com.lorenzoog.zipzop.config.auth

import com.auth0.jwt.JWT
import com.auth0.jwt.algorithms.Algorithm
import com.lorenzoog.zipzop.entities.User
import io.ktor.auth.jwt.JWTPrincipal
import io.ktor.auth.jwt.jwt
import io.ktor.config.ApplicationConfig
import io.ktor.sessions.SessionTransportTransformer
import io.ktor.sessions.header
import io.ktor.util.KtorExperimentalAPI
import org.kodein.di.DI
import org.kodein.di.instance
import io.ktor.auth.Authentication.Configuration as AuthConfiguration
import io.ktor.sessions.Sessions.Configuration as SessionsConfiguration

const val AUTHORIZATION_HEADER = "Authorization"

data class UserSession(val user: User)

@OptIn(KtorExperimentalAPI::class)
fun AuthConfiguration.setup(di: DI, config: ApplicationConfig) {
  val jwtIssuer = config.property("domain").getString()
  val jwtAudience = config.property("audience").getString()

  jwt {
    realm = config.property("realm").getString()

    val algorithm by di.instance<Algorithm>()

    verifier(
      JWT.require(algorithm)
        .withAudience(jwtAudience)
        .withIssuer(jwtIssuer)
        .build()
    )

    validate { credentials ->
      if (credentials.payload.audience.contains(jwtAudience))
        JWTPrincipal(credentials.payload)
      else null
    }
  }
}

fun SessionsConfiguration.setup() {
  header<UserSession>(AUTHORIZATION_HEADER)
}

