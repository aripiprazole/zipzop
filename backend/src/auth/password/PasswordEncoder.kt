package com.lorenzoog.zipzop.config.auth.password

interface PasswordEncoder {

  fun matches(password: String, hashedPassword: String): Boolean
  fun encode(password: String): String

}
