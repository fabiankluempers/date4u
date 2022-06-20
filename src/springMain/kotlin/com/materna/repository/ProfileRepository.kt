package com.materna.repository

import com.materna.entity.Profile
import org.springframework.data.jpa.repository.JpaRepository

interface ProfileRepository : JpaRepository<Profile, Long>{
  fun existsByNickname(nickname: String) : Boolean

  fun findByNickname(nickname: String) : Profile
}
