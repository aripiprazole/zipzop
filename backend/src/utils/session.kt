package me.devgabi.zipzop.utils

import me.devgabi.zipzop.exceptions.AuthorizationException
import me.devgabi.zipzop.config.UserSession
import io.ktor.sessions.CurrentSession
import io.ktor.sessions.get

fun CurrentSession.current(): UserSession =
  get<UserSession>() ?: throw AuthorizationException()
