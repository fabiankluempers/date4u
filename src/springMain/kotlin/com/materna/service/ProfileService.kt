package com.materna.service

import com.materna.entity.Profile
import com.materna.entity.Unicorn
import com.materna.repository.ProfileRepository
import com.materna.repository.UnicornRepository
import com.materna.security.UnicornDetails
import dto.ProfileDTO
import org.springframework.stereotype.Service
import javax.transaction.Transactional

@Service
class ProfileService(
  private val profileRepository: ProfileRepository,
  private val unicornRepository: UnicornRepository,
) {

  context(UnicornDetails)
  fun profileByEmail() = unicornRepository
    .findUnicornByEmail(email)
    ?.profile
    ?.toProfileDTO()


  context(UnicornDetails) @Transactional
  fun updateProfile(profileDTO: ProfileDTO): Result<ProfileDTO> = runCatching {
    val unicorn = unicornRepository.findUnicornByEmail(email) ?: throw UnicornNotFoundException()
    checkAuth(unicorn)
    if (profileRepository.existsByNickname(profileDTO.nickname))
      throw UniqueConstraintException.of(Profile::nickname)
    val profile = unicorn.profile.copy(
      nickname = profileDTO.nickname,
      hornLength = profileDTO.hornLength.toShort(),
      gender = profileDTO.gender.toShort(),
      attractedToGender = profileDTO.gender.toShort(),
      description = profileDTO.description, // TODO prevent xss?
    )
    profileRepository.save(profile)
    profile.toProfileDTO()
  }

  context(UnicornDetails)
      private fun checkAuth(unicorn: Unicorn) =
    if (email == unicorn.email) Unit else throw UnicornUnauthorizedException()
}
