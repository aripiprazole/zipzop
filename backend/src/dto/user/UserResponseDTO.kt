package com.lorenzoog.zipzop.dto.user

import com.lorenzoog.zipzop.entities.User
import kotlinx.serialization.Serializable

@Serializable
data class UserResponseDTO(
  val username: String
)

fun User.toDto() = UserResponseDTO(
  username
)
