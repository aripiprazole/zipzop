package com.lorenzoog.zipzop.entities

import com.lorenzoog.zipzop.tables.Users
import org.jetbrains.exposed.dao.LongEntity
import org.jetbrains.exposed.dao.LongEntityClass
import org.jetbrains.exposed.dao.id.EntityID

class User(id: EntityID<Long>) : LongEntity(id) {
  var username: String by Users.username
  var password: String by Users.password

  companion object : LongEntityClass<User>(Users)
}
