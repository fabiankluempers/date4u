package com.materna.service

import dto.ErrorDTO
import kotlin.reflect.KProperty

class UniqueConstraintException(val property: String) : Exception() {
  fun toErrorDTO() = ErrorDTO(
	error = this::class.simpleName.toString(),
	message = "$property must be unique",
	detail = "${
	  property.first().uppercaseChar() + property.substring(1)
	} is already taken, please select another $property.",
  )

  companion object {
	fun <T> of(prop: KProperty<T>) = UniqueConstraintException(prop.name)


  }
}
