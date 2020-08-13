package com.lorenzoog.zipzop.services.user

import com.lorenzoog.zipzop.auth.password.PasswordEncoder
import com.lorenzoog.zipzop.dto.user.UserCreateDTO
import com.lorenzoog.zipzop.dto.user.UserUpdateDTO
import com.lorenzoog.zipzop.entities.User
import com.lorenzoog.zipzop.tables.Users
import org.jetbrains.exposed.dao.exceptions.EntityNotFoundException
import org.jetbrains.exposed.dao.id.EntityID
import org.jetbrains.exposed.sql.Op
import org.jetbrains.exposed.sql.transactions.experimental.newSuspendedTransaction
import org.koin.core.KoinComponent
import org.koin.core.inject

class ExposedUserService : UserService, KoinComponent {
  private val passwordEncoder by inject<PasswordEncoder>()

  override suspend fun create(data: UserCreateDTO) = newSuspendedTransaction {
    User.new {
      username = data.username
      password = passwordEncoder.encode(data.password)
    }
  }

  override suspend fun findAll(page: Int) = newSuspendedTransaction {
    val limit = page * PAGE_SIZE
    val offset = (page + PAGE_SIZE).toLong()

    User.find(Op.TRUE).limit(limit, offset)
  }

  override suspend fun findByUsername(username: String) = newSuspendedTransaction {
    User.find { Users.username eq username }
      .limit(1)
      .firstOrNull() ?: throw EntityNotFoundException(EntityID(0.toLong(), Users), User)
  }

  override suspend fun findById(id: Long) = newSuspendedTransaction {
    User.findById(id) ?: throw EntityNotFoundException(EntityID(id, Users), User)
  }

  override suspend fun updateById(id: Long, newData: UserUpdateDTO) = newSuspendedTransaction {
    findById(id).update(newData)
  }

  override suspend fun updateByUsername(username: String, newData: UserUpdateDTO) = newSuspendedTransaction {
    findByUsername(username).update(newData)
  }

  override suspend fun deleteById(id: Long) = newSuspendedTransaction {
    findById(id).delete()
  }

  private fun User.update(newData: UserUpdateDTO) = apply {
    newData.username?.let { username = it }
    newData.password?.let { password = passwordEncoder.encode(it) }
  }

  companion object {
    private const val PAGE_SIZE = 15
  }
}
