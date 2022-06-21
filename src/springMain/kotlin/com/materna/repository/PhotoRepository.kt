package com.materna.repository

import com.materna.entity.Photo
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.data.jpa.repository.Query

interface PhotoRepository : JpaRepository<Photo, Long> {

  fun findByProfileNickname(nickname: String): List<Photo>

  @Query(
	"select photo from Photo photo " +
		"where photo.profile.nickname = :nickname " +
		"and photo.isProfilePhoto = true "
  )
  fun findProfilePhotoByNickname(nickname: String): List<Photo>
}
