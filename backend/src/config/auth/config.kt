package com.lorenzoog.zipzop.config.auth

import com.lorenzoog.zipzop.entities.User
import io.ktor.auth.Authentication.Configuration
import io.ktor.auth.basic
import io.ktor.sessions.get
import io.ktor.sessions.sessions

data class UserPrincipal(val user: User)

fun Configuration.setup() {
  basic("jwt") {
    validate { // TODO
      null
    }

    skipWhen { call -> call.sessions.get<UserPrincipal>() != null }
  }
}
