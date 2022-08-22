package me.devgabi.zipzop.tables

import org.jetbrains.exposed.dao.id.LongIdTable

object Users : LongIdTable("users") {
  val username = varchar("username", 32).uniqueIndex()
  val password = varchar("password", 128) // password hash
}
