package com.lorenzoog.zipzop.dto.exceptions

import kotlinx.serialization.Serializable

@Serializable
data class JsonDecodingExceptionDTO(
  val message: String
)
