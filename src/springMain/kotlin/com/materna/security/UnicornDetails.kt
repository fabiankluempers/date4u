package com.materna.security

import org.springframework.security.core.GrantedAuthority
import org.springframework.security.core.userdetails.UserDetails

class UnicornDetails(val email: String, private val password: String) : UserDetails {

	override fun getAuthorities(): MutableCollection<out GrantedAuthority> = mutableListOf()

	override fun getPassword(): String = password

	override fun getUsername(): String = email

	override fun isAccountNonExpired(): Boolean = true

	override fun isAccountNonLocked(): Boolean = true

	override fun isCredentialsNonExpired(): Boolean = true

	override fun isEnabled(): Boolean = true
}
