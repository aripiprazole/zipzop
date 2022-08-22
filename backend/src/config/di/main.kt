package me.devgabi.zipzop.config.di

import me.devgabi.zipzop.services.user.ExposedUserService
import me.devgabi.zipzop.services.user.UserService
import org.koin.dsl.module

fun mainModule() = module {
  single<UserService> { ExposedUserService() }
}
