package com.materna.controller


import com.materna.entity.Profile
import com.materna.service.ProfileService
import dto.RangeDTO
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RestController

@RestController
class ConstraintsController(private val profileService: ProfileService) {

  @GetMapping("/constraint/genders")
  fun genders() = Profile.Companion.Gender.asList

  @GetMapping("/constraint/age_range")
  fun ageRange() = RangeDTO.fromRange(profileService.ageRange())

  @GetMapping("/constraint/horn_length_range")
  fun hornLengthRange() = RangeDTO.fromRange(Profile.validHornLength)
}