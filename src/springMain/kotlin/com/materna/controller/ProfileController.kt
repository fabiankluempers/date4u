package com.materna.controller

import com.materna.repository.UnicornRepository
import com.materna.security.UnicornDetails
import org.springframework.security.core.Authentication
import org.springframework.stereotype.Controller
import org.springframework.web.bind.annotation.GetMapping

@Controller
class ProfileController(private val unicornRepository: UnicornRepository) {

	@GetMapping("/myProfile")
	fun myProfile(authentication: Authentication) = unicornRepository
		.findUnicornByEmail((authentication.details as UnicornDetails).email)
		?.profile
		?.toProfileDTO()
}