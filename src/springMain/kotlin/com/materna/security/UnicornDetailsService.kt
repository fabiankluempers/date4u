package com.materna.security

import com.materna.repository.UnicornRepository
import org.springframework.security.core.userdetails.UserDetails
import org.springframework.security.core.userdetails.UserDetailsService
import org.springframework.security.core.userdetails.UsernameNotFoundException
import org.springframework.stereotype.Service

@Service
class UnicornDetailsService(private val unicornRepo: UnicornRepository) : UserDetailsService {
	override fun loadUserByUsername(username: String): UserDetails =
		unicornRepo.findUnicornByEmail(username)
			?.let { UnicornDetails(it.email, it.password) }
			?: throw UsernameNotFoundException(username)
}