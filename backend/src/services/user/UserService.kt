package com.lorenzoog.zipzop.services.user

import com.lorenzoog.zipzop.dto.user.UserCreateDTO
import com.lorenzoog.zipzop.dto.user.UserUpdateDTO
import com.lorenzoog.zipzop.entities.User

interface UserService {
 suspend fun create(data: UserCreateDTO): User

  suspend fun findAll(page: Int): Iterable<User>

  suspend fun findByUsername(username: String): User
  suspend fun findById(id: Long): User

  suspend fun updateById(id: Long, newData: UserUpdateDTO): User
  suspend fun updateByUsername(username: String, newData: UserUpdateDTO): User

  suspend fun deleteById(id: Long)
}
