package com.lorenzoog.zipzop.exceptions

import java.lang.RuntimeException

class UniqueFieldViolationException(val field: String) : RuntimeException("Already exists an entity with field $field")
