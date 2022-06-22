package com.materna.service

import com.materna.entity.Profile
import com.materna.entity.Unicorn
import com.materna.repository.ProfileRepository
import com.materna.repository.UnicornRepository
import com.materna.security.UnicornDetails
import dto.ProfileDTO
import org.springframework.stereotype.Service
import java.time.LocalDate
import java.time.Period
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

  fun all() = profileRepository.findAll().toList()

  fun ageRange() = with(profileRepository) {
	val now = LocalDate.now()
	Period.between(lastBirthdate(), now).years..Period.between(firstBirthdate(), now).years
  }


  context(UnicornDetails) @Transactional
  fun updateProfile(profileDTO: ProfileDTO): Result<ProfileDTO> = runCatching {
	val unicorn = unicornRepository.findUnicornByEmail(email) ?: throw UnicornNotFoundException()

	checkAuth(unicorn)

	if (
	  profileDTO.nickname != unicorn.profile.nickname &&
	  profileRepository.existsByNickname(profileDTO.nickname)
	) {
	  println("tuherjsbfgdkjgdfj,gjdfk")
	  throw UniqueConstraintException.of(Profile::nickname)
	}

	val profile = unicorn.profile.copy(
	  nickname = profileDTO.nickname,
	  hornLength = profileDTO.hornLength.toShort(),
	  gender = profileDTO.gender.toShort(),
	  attractedToGender = profileDTO.gender.toShort(),
	  description = profileDTO.description,
	)

	profileRepository.save(profile)

	profile.toProfileDTO()
  }

  context(UnicornDetails)
	  private fun checkAuth(unicorn: Unicorn) =
	if (email == unicorn.email) Unit else throw UnicornUnauthorizedException()
}
