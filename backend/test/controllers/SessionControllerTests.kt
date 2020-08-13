package com.lorenzoog.controllers

import com.lorenzoog.withMocksApplication
import com.lorenzoog.zipzop.Json
import com.lorenzoog.zipzop.auth.JwtService
import com.lorenzoog.zipzop.auth.password.PasswordEncoder
import com.lorenzoog.zipzop.controllers.Login
import com.lorenzoog.zipzop.dto.auth.LoginResponseDTO
import com.lorenzoog.zipzop.dto.user.UserCreateDTO
import com.lorenzoog.zipzop.dto.user.UserResponseDTO
import com.lorenzoog.zipzop.dto.user.toDto
import com.lorenzoog.zipzop.services.user.UserService
import io.ktor.http.ContentType
import io.ktor.http.HttpHeaders
import io.ktor.http.HttpMethod
import io.ktor.http.HttpStatusCode
import io.ktor.server.testing.handleRequest
import io.ktor.server.testing.setBody
import kotlinx.coroutines.runBlocking
import org.junit.Test
import org.koin.ktor.ext.inject
import kotlin.test.assertEquals

class SessionControllerTests {
  private object PasswordEncoderMock : PasswordEncoder {
    override fun matches(password: String, hashedPassword: String) = true

    override fun encode(password: String) = password
  }

  private val testApp = withMocksApplication {
    single<PasswordEncoder>(override = true) { PasswordEncoderMock }
  }

  @Test
  fun testAuthentication(): Unit = testApp {
    val userService by application.inject<UserService>()
    val jwtService by application.inject<JwtService>()

    val username = "fake username"
    val password = "fake password"

    val user = runBlocking {
      userService.create(UserCreateDTO(
        username,
        password
      ))
    }

    handleRequest(HttpMethod.Post, "/login") {
      addHeader(HttpHeaders.ContentType, ContentType.Application.Json.toString())
      addHeader(HttpHeaders.Accept, ContentType.Application.Json.toString())

      setBody(Json.stringify(Login.Body.serializer(), Login.Body(
        username,
        password
      )))
    }.apply {
      val token = Json.parse(LoginResponseDTO.serializer(), response.content.toString())

      assertEquals(HttpStatusCode.OK, response.status())
      assertEquals(user.id, runBlocking {
        jwtService.transformJwtToUser(token.jwt).id
      })
    }
  }

  @Test
  fun testAuthorization(): Unit = testApp {
    val userService by application.inject<UserService>()
    val jwtService by application.inject<JwtService>()

    val user = runBlocking {
      userService.create(UserCreateDTO(
        username = "username",
        password = "password"
      ))
    }

    val token = jwtService.transformUserToJwt(user)

    handleRequest(HttpMethod.Get, "/session") {
      addHeader(HttpHeaders.ContentType, ContentType.Application.Json.toString())
      addHeader(HttpHeaders.Accept, ContentType.Application.Json.toString())
      addHeader(HttpHeaders.Authorization, token)
    }.apply {
      assertEquals(HttpStatusCode.OK, response.status())
      assertEquals(Json.stringify(UserResponseDTO.serializer(), user.toDto()), response.content)
    }
  }
}
