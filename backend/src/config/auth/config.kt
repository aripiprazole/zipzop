package com.lorenzoog.zipzop.config.auth

import com.auth0.jwt.JWT
import com.auth0.jwt.algorithms.Algorithm
import io.ktor.auth.Authentication.Configuration
import io.ktor.auth.jwt.JWTPrincipal
import io.ktor.auth.jwt.jwt
import io.ktor.config.ApplicationConfig
import io.ktor.util.KtorExperimentalAPI
import org.kodein.di.DI
import org.kodein.di.instance

@OptIn(KtorExperimentalAPI::class)
fun Configuration.setup(di: DI, config: ApplicationConfig) {
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

