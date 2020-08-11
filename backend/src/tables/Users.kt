package com.lorenzoog.zipzop.tables

import org.jetbrains.exposed.dao.id.LongIdTable

object Users : LongIdTable("users") {
  val username = varchar("username", 32)
  val password = varchar("password", 72) // password hash
}
