package com.lorenzoog.zipzop.config.kodein

import com.lorenzoog.zipzop.services.user.ExposedUserService
import com.lorenzoog.zipzop.services.user.UserService
import io.ktor.config.ApplicationConfig
import io.ktor.util.KtorExperimentalAPI
import org.kodein.di.DI
import org.kodein.di.DI.MainBuilder
import org.kodein.di.bind
import org.kodein.di.singleton

const val MAIN_MODULE_NAME = "Main module"

@OptIn(KtorExperimentalAPI::class)
fun MainBuilder.setup(config: ApplicationConfig) {
  import(DI.Module(MAIN_MODULE_NAME) {
    bind<UserService>() with singleton { ExposedUserService(di) }
  })
  import(authModule(config.config("ktor.jwt")))
}
