package com.lorenzoog.zipzop.config.auth

import com.auth0.jwt.JWT
import com.auth0.jwt.algorithms.Algorithm
import io.ktor.auth.Authentication.Configuration
import io.ktor.auth.jwt.JWTPrincipal
import io.ktor.auth.jwt.jwt
import io.ktor.config.ApplicationConfig
import io.ktor.util.KtorExperimentalAPI
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import org.kodein.di.DI
import org.kodein.di.instance
import java.nio.file.Files
import java.nio.file.Path
import java.security.KeyFactory
import java.security.spec.PKCS8EncodedKeySpec
import java.security.spec.X509EncodedKeySpec

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

@Suppress("UNCHECKED_CAST")
suspend fun <PrivateKeyType, PublicKeyType> KeyFactory.getKeyPair(
  publicKeyPath: String,
  privateKeyPath: String
): Pair<PublicKeyType, PrivateKeyType> {
  val publicKey = withContext(Dispatchers.IO) {
    val bytes: ByteArray = Files.readAllBytes(Path.of(publicKeyPath))

    generatePublic(X509EncodedKeySpec(bytes)) as PublicKeyType
  }
  val privateKey = withContext(Dispatchers.IO) {
    val bytes: ByteArray = Files.readAllBytes(Path.of(privateKeyPath))

    generatePrivate(PKCS8EncodedKeySpec(bytes)) as PrivateKeyType
  }

  return publicKey to privateKey
}
