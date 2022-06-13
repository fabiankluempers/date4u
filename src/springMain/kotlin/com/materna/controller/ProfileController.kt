package com.materna.controller

import com.materna.repository.UnicornRepository
import com.materna.security.UnicornDetails
import org.springframework.security.core.Authentication
import org.springframework.stereotype.Controller
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.ResponseBody

@Controller
class ProfileController(private val unicornRepository: UnicornRepository) {

	@GetMapping("/myProfile")
	@ResponseBody
	fun myProfile(authentication: Authentication) = unicornRepository
		.findUnicornByEmail((authentication.principal as UnicornDetails).email)
		?.profile
		?.toProfileDTO().also { println("fetched") }
}
