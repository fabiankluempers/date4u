package com.materna.repository

import com.materna.entity.Profile
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.data.jpa.repository.Query
import java.time.LocalDate

interface ProfileRepository : JpaRepository<Profile, Long> {

  @Query("select profile.likers from Profile profile where profile.nickname = :nickname")
  fun findLikersByNickname(nickname: String): List<Profile>
  @Query("select profile.likees from Profile profile where profile.nickname = :nickname")
  fun findLikeesByNickname(nickname: String): List<Profile>

  fun existsByNickname(nickname: String): Boolean

  fun findByNickname(nickname: String): Profile?

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
