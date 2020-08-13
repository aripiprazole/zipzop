package com.lorenzoog.zipzop.dto.user

import kotlinx.serialization.Serializable

sealed class UserDTO {
  abstract val username: String?
  abstract val password: String?
}

@Serializable
data class UserUpdateDTO(
  override val username: String?,
  override val password: String?
) : UserDTO()

@Serializable
data class UserCreateDTO(
  override val username: String,
  override val password: String
) : UserDTO()
