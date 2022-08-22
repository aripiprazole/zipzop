package me.devgabi.zipzop.dto.auth

import kotlinx.serialization.Serializable

@Serializable
data class LoginResponseDTO(val jwt: String)
