package com.lorenzoog.zipzop.config.kodein

import com.auth0.jwt.algorithms.Algorithm
import com.lorenzoog.zipzop.config.auth.getKeyPair
import io.ktor.config.ApplicationConfig
import io.ktor.util.KtorExperimentalAPI
import kotlinx.coroutines.runBlocking
import org.kodein.di.DI
import org.kodein.di.bind
import org.kodein.di.singleton
import java.security.KeyFactory
import java.security.interfaces.RSAPrivateKey
import java.security.interfaces.RSAPublicKey

@OptIn(KtorExperimentalAPI::class)
fun jwtModule(config: ApplicationConfig) =
  DI.Module("Jwt module") {
    bind<Algorithm>() with singleton {
      runBlocking<Algorithm> {
        val rsaPrivateKeyPath = config.property("key.private").getString()
        val rsaPublicKeyPath = config.property("key.public").getString()

        val keyFactory: KeyFactory = KeyFactory.getInstance("RSA")
        val (publicKey, privateKey) = keyFactory
          .getKeyPair<RSAPublicKey, RSAPrivateKey>(rsaPrivateKeyPath, rsaPublicKeyPath)

        Algorithm.RSA512(privateKey, publicKey)
      }
    }
  }
