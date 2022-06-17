package com.materna.service

import kotlin.reflect.KProperty

class UniqueConstraintException(val property: String) : Exception() {
  companion object {
    fun <T> of(prop: KProperty<T>) = UniqueConstraintException(prop.name)
  }
}
