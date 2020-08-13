package com.lorenzoog.zipzop.config.di

import com.auth0.jwt.algorithms.Algorithm
import com.lorenzoog.zipzop.auth.JwtService
import com.lorenzoog.zipzop.auth.password.Argon2PasswordEncoder
import com.lorenzoog.zipzop.auth.password.PasswordEncoder
import io.ktor.config.ApplicationConfig
import io.ktor.util.KtorExperimentalAPI
import org.koin.dsl.module

@OptIn(KtorExperimentalAPI::class)
fun authModule(config: ApplicationConfig) = module {
  single<PasswordEncoder> { Argon2PasswordEncoder() }

  single<Algorithm> {
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

  single { JwtService(config) }
}
