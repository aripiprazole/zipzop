package com.lorenzoog.zipzop.services.user

import com.lorenzoog.zipzop.dto.user.UserCreateDTO
import com.lorenzoog.zipzop.dto.user.UserUpdateDTO
import com.lorenzoog.zipzop.entities.User

class ExposedUserService : UserService {
  override suspend fun create(data: UserCreateDTO): User {
    TODO("Not yet implemented")
  }

  override suspend fun findAll(page: Int): Iterable<User> {
    TODO("Not yet implemented")
  }

  override suspend fun findByUsername(username: String): User {
    TODO("Not yet implemented")
  }

  override suspend fun findById(id: Long): User {
    TODO("Not yet implemented")
  }

  override suspend fun updateById(id: Long, newData: UserUpdateDTO): User {
    TODO("Not yet implemented")
  }

  override suspend fun updateByUsername(username: String, newData: UserUpdateDTO): User {
    TODO("Not yet implemented")
  }

  override suspend fun deleteById(id: Long): User {
    TODO("Not yet implemented")
  }
}
