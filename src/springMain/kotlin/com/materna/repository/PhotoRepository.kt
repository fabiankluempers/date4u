package com.materna.repository

import com.materna.entity.Photo
import org.springframework.data.jpa.repository.JpaRepository

interface PhotoRepository : JpaRepository<Photo, Long> {

  fun findByProfileNickname(nickname: String) : List<Photo>
}
