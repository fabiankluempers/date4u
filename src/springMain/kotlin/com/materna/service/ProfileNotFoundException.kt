package com.materna.service

import dto.ErrorDTO

class ProfileNotFoundException : Exception() {
  fun toErrorDTO() = ErrorDTO(
	error = this::class.simpleName.toString(),
	message = "Profile was not found",
	detail = "The requested profile was not found.",
  )
}
