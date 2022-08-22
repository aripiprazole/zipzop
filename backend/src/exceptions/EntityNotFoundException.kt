package me.devgabi.zipzop.exceptions

import java.lang.RuntimeException

class EntityNotFoundException(override val message: String) : RuntimeException(message)
