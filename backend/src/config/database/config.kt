package com.lorenzoog.zipzop.config.database

import io.ktor.config.ApplicationConfig
import io.ktor.util.KtorExperimentalAPI
import org.jetbrains.exposed.sql.Database

object DatabaseInitializer {

  @KtorExperimentalAPI
  fun setupDatabase(config: ApplicationConfig) {
    val url = config.property("url").getString()
    val driver = config.property("driver").getString()
    val user = config.property("user").getString()
    val password = config.property("password").getString()

    Database.connect(url, driver, user, password)
  }

}
