package com.materna.controller

import com.materna.entity.Profile
import com.materna.security.UnicornDetails
import com.materna.service.ProfileService
import com.materna.service.UnicornNotFoundException
import com.materna.service.UnicornUnauthorizedException
import com.materna.service.UniqueConstraintException
import dto.ProfileDTO
import dto.RestError
import kotlinx.serialization.json.Json
import org.springframework.http.ResponseEntity
import org.springframework.security.core.Authentication
import org.springframework.stereotype.Controller
import org.springframework.web.bind.annotation.*
import kotlin.reflect.full.companionObject

@RestController
class ProfileController(private val profileService: ProfileService) {

  @GetMapping("/profile")
  fun myProfile(authentication: Authentication) =
    with(authentication.unicornDetails) {
      profileService.profileByEmail()
    }

  @PutMapping("/profile")
  fun updateMyProfile(authentication: Authentication, @RequestBody profile: ProfileDTO) =
    with(authentication.unicornDetails) {
      profileService.updateProfile(profile).also { println(it) }
    }

  @GetMapping("/profile/constraints")
  fun constraints() = Profile.toConstraintsDTO()


  private val Authentication.unicornDetails
    get() = principal as UnicornDetails
}
