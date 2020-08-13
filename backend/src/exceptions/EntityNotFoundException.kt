package com.lorenzoog.zipzop.exceptions

import java.lang.RuntimeException

class EntityNotFoundException(override val message: String) : RuntimeException(message)
