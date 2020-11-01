package com.lorenzoog.zipzop.utils

import com.lorenzoog.zipzop.exceptions.AuthorizationException
import com.lorenzoog.zipzop.config.UserSession
import io.ktor.sessions.CurrentSession
import io.ktor.sessions.get

fun CurrentSession.current(): UserSession =
  get<UserSession>() ?: throw AuthorizationException()
