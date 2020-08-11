package com.lorenzoog.zipzop.config.kodein

import com.auth0.jwt.algorithms.Algorithm
import io.ktor.config.ApplicationConfig
import io.ktor.util.KtorExperimentalAPI
import org.kodein.di.DI
import org.kodein.di.bind
import org.kodein.di.singleton

@OptIn(KtorExperimentalAPI::class)
fun jwtModule(config: ApplicationConfig) =
  DI.Module("Jwt module") {
    bind<Algorithm>() with singleton {
      Algorithm.HMAC256("temp secret")
//      TODO: use RSA512 instead of HMAC256
//      runBlocking<Algorithm> {
//        val rsaPrivateKeyPath = config.property("key.private").getString()
//        val rsaPublicKeyPath = config.property("key.public").getString()
//
//        val keyFactory: KeyFactory = KeyFactory.getInstance("RSA")
//        val (publicKey, privateKey) = keyFactory
//          .getKeyPair<RSAPublicKey, RSAPrivateKey>(rsaPrivateKeyPath, rsaPublicKeyPath)
//
//        Algorithm.RSA512(privateKey, publicKey)
//      }
    }
  }
