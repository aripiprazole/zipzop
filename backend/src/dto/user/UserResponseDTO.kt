package me.devgabi.zipzop.dto.user

import me.devgabi.zipzop.entities.User
import kotlinx.serialization.Serializable

@Serializable
data class UserResponseDTO(
  val username: String
)

fun User.toDto() = UserResponseDTO(
  username
)
