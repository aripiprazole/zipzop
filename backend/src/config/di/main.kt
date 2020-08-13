package com.lorenzoog.zipzop.config.di

import com.lorenzoog.zipzop.services.user.ExposedUserService
import com.lorenzoog.zipzop.services.user.UserService
import org.koin.dsl.module

fun mainModule() = module {
  single<UserService> { ExposedUserService() }
}
