package com.lorenzoog.zipzop.config.kodein

import com.lorenzoog.zipzop.services.user.ExposedUserService
import com.lorenzoog.zipzop.services.user.UserService
import org.kodein.di.DI
import org.kodein.di.DI.MainBuilder
import org.kodein.di.bind
import org.kodein.di.singleton

const val MAIN_MODULE_NAME = "Main module"

fun MainBuilder.setup() {
  import(DI.Module(MAIN_MODULE_NAME) {
    bind<UserService>() with singleton { ExposedUserService() }
  })
}
