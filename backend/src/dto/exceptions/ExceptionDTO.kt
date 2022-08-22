package me.devgabi.zipzop.dto.exceptions

import kotlinx.serialization.Serializable

@Serializable
data class ExceptionDTO(
  val message: String
)
