package me.devgabi.zipzop.config.di

import com.auth0.jwt.algorithms.Algorithm
import me.devgabi.zipzop.auth.JwtService
import me.devgabi.zipzop.auth.password.Argon2PasswordEncoder
import me.devgabi.zipzop.auth.password.PasswordEncoder
import io.ktor.config.ApplicationConfig
import io.ktor.util.KtorExperimentalAPI
import org.koin.dsl.module

@OptIn(KtorExperimentalAPI::class)
fun authModule(config: ApplicationConfig) = module {
  single<PasswordEncoder> { Argon2PasswordEncoder() }

  single<Algorithm> {
    Algorithm.HMAC256(config.propertyOrNull("secret")?.getString().orEmpty())
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
