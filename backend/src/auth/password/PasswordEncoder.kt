package com.lorenzoog.zipzop.auth.password

interface PasswordEncoder {

  fun matches(password: String, hashedPassword: String): Boolean
  fun encode(password: String): String

}
