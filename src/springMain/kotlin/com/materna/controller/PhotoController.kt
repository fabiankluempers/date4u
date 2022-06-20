package com.materna.controller

import com.materna.entity.Photo
import com.materna.repository.PhotoRepository
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.RestController

@RestController
class PhotoController(private val photoRepository: PhotoRepository) {

  @GetMapping("/profile/{nickname}/photos")
  fun photosByProfile(@PathVariable nickname: String) = photoRepository
	  .findByProfileNickname(nickname)
	  .map(Photo::toPhotoDTO)
}