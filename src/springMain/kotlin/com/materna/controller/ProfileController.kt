package com.materna.controller

import com.materna.entity.Profile
import com.materna.security.UnicornDetails
import com.materna.service.ProfileService
import com.materna.service.UnicornNotFoundException
import com.materna.service.UnicornUnauthorizedException
import com.materna.service.UniqueConstraintException
import dto.ErrorDTO
import dto.ProfileDTO
import dto.ResponseDTO
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
      profileService.updateProfile(profile)
    }.map {
      ResponseDTO.success(it)
    }.getOrElse {
      ResponseDTO.failure(
        when (it) {
          is UniqueConstraintException -> it.toErrorDTO()
          else -> ErrorDTO(error = "Internal server error")
        }
      )
    }

  @GetMapping("/profile/all")
  fun profiles() = profileService.all().map(Profile::toProfileDTO)

  @GetMapping("/profile/search")
  fun search(
    @RequestParam minAge: Int,
    @RequestParam maxAge: Int,
    @RequestParam minHornLength: Int,
    @RequestParam maxHornLength: Int,
    @RequestParam interestedIn: List<Short>?,
  ) = profileService.search(
    ageRange = minAge..maxAge,
    hornLengthRange = minHornLength..maxHornLength,
    interestedIn = interestedIn?.toSet() ?: setOf()
  ).map(Profile::toProfileDTO)


  private val Authentication.unicornDetails
    get() = principal as UnicornDetails
}
