package com.materna.repository

import com.materna.entity.Profile
import net.bytebuddy.implementation.bytecode.collection.ArrayLength
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.data.jpa.repository.Query
import java.time.LocalDate

interface ProfileRepository : JpaRepository<Profile, Long> {
  fun existsByNickname(nickname: String): Boolean

  fun findByNickname(nickname: String): Profile

  @Query(
	"select profile from Profile  profile " +
		"where profile.birthdate between :lowerDate and :upperDate " +
		"and profile.hornLength between :lowerHornLength and :upperHornLength " +
		"and profile.gender in :interestedIn"
  )
  fun findByFilter(
	lowerDate: LocalDate,
	upperDate: LocalDate,
	lowerHornLength: Short,
	upperHornLength: Short,
	interestedIn: Set<Short>,
  ): List<Profile>

  @Query("select min(profile.birthdate) from Profile profile")
  fun firstBirthdate(): LocalDate

  @Query("select max(profile.birthdate) from Profile profile")
  fun lastBirthdate(): LocalDate
}
