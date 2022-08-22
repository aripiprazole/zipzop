package me.devgabi.zipzop.services.user

import me.devgabi.zipzop.dto.user.UserCreateDTO
import me.devgabi.zipzop.dto.user.UserUpdateDTO
import me.devgabi.zipzop.entities.User

interface UserService {
  suspend fun create(data: UserCreateDTO): User

  suspend fun findAll(page: Int): Iterable<User>

  suspend fun findByUsername(username: String): User
  suspend fun findById(id: Long): User

  suspend fun updateById(id: Long, newData: UserUpdateDTO): User
  suspend fun updateByUsername(username: String, newData: UserUpdateDTO): User

  suspend fun deleteById(id: Long)
}
