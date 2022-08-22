package me.devgabi.zipzop.dto.messages

import kotlinx.serialization.Serializable

@Serializable
data class TextMessagePacket(
  val message: String,
  val targetId: Long
)
