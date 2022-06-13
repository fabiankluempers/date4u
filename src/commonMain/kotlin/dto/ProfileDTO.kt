package dto

import kotlinx.datetime.LocalDate
import kotlinx.datetime.LocalDateTime

data class ProfileDTO(
	val nickname : String,
	val hornLength: Int,
	val birthdate: LocalDate,
	val gender: Int,
	val attractedToGender: Int?,
	val description: String,
	val lastSeen: LocalDateTime,
)
