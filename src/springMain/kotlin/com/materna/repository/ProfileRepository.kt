package com.materna.repository

import com.materna.entity.Profile
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.data.jpa.repository.Query
import java.time.LocalDate

interface ProfileRepository : JpaRepository<Profile, Long>{
  fun existsByNickname(nickname: String) : Boolean

  fun findByNickname(nickname: String) : Profile

  @Query("select min(profile.birthdate) from Profile profile")
  fun firstBirthdate() : LocalDate

  @Query("select max(profile.birthdate) from Profile profile")
  fun lastBirthdate() : LocalDate
}
