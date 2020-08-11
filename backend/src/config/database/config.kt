package com.lorenzoog.zipzop.config.database

import com.lorenzoog.zipzop.tables.Users
import io.ktor.config.ApplicationConfig
import io.ktor.util.KtorExperimentalAPI
import kotlinx.coroutines.runBlocking
import org.jetbrains.exposed.sql.Database
import org.jetbrains.exposed.sql.SchemaUtils
import org.jetbrains.exposed.sql.transactions.experimental.newSuspendedTransaction

object DatabaseInitializer {

  @KtorExperimentalAPI
  fun setupDatabase(config: ApplicationConfig) = runBlocking<Unit> {
    val url = config.property("url").getString()
    val driver = config.property("driver").getString()
    val user = config.property("user").getString()
    val password = config.property("password").getString()

    Database.connect(url, driver, user, password).also {
      newSuspendedTransaction(db = it) {
        SchemaUtils.create(Users)
      }
    }
  }

}
