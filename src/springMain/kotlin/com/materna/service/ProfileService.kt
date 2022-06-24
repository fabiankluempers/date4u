package com.materna.service

import com.materna.entity.Profile
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
  fun likesByNickname(nickname: String) = profileRepository.findLikeesByNickname(nickname)

  fun likersByNickname(nickname: String) = profileRepository.findLikersByNickname(nickname)

  context(UnicornDetails)
  private fun transformLikes(nickname: String, operation: (Set<Profile>, Profile) -> Set<Profile>) = runCatching {
	val profile = unicornRepository.findUnicornByEmail(email)?.profile ?: throw UnicornNotFoundException()

	val likee = profileRepository.findByNickname(nickname) ?: throw ProfileNotFoundException()

	profileRepository.save(profile.copy(likees = operation(profile.likees, likee)))
  }

  context(UnicornDetails)
  fun like(nickname: String) = transformLikes(nickname, Set<Profile>::plus)

  context(UnicornDetails)
  fun dislike(nickname: String) = transformLikes(nickname, Set<Profile>::minus)

  context(UnicornDetails)
  fun profileByEmail() = unicornRepository
	.findUnicornByEmail(email)
	?.profile

  fun all() = profileRepository.findAll().toList()

  fun ageRange() = with(profileRepository) {
	val now = LocalDate.now()
	Period.between(lastBirthdate(), now).years..Period.between(firstBirthdate(), now).years
  }


  context(UnicornDetails) @Transactional
  fun updateProfile(profileDTO: ProfileDTO): Result<Profile> = runCatching {
	val unicorn = unicornRepository.findUnicornByEmail(email) ?: throw UnicornNotFoundException()

	if (
	  profileDTO.nickname != unicorn.profile.nickname &&
	  profileRepository.existsByNickname(profileDTO.nickname)
	) {
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
  }

  fun search(ageRange: IntRange, hornLengthRange: IntRange, interestedIn: Set<Short>): List<Profile> {
	val (firstBirthdate, lastBirthdate) = ageRange.toDates()
	return profileRepository.findByFilter(
	  lowerDate = firstBirthdate,
	  upperDate = lastBirthdate,
	  lowerHornLength = hornLengthRange.first.toShort(),
	  upperHornLength = hornLengthRange.last.toShort(),
	  interestedIn = interestedIn
	)
  }

  private fun IntRange.toDates(): Pair<LocalDate, LocalDate> {
	val now = LocalDate.now()
	return Pair(
	  now.minusYears(last.toLong()),
	  now.minusYears(first.toLong())
	)
  }

  fun profileByNickname(nickname: String): Profile? = profileRepository.findByNickname(nickname)
}
