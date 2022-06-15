package dto

import kotlinx.datetime.LocalDate
import kotlinx.datetime.LocalDateTime
import kotlinx.serialization.Serializable

@Serializable
data class ProfileDTO(
  val nickname: String,
  val hornLength: Int,
  val birthdate: LocalDate,
  val gender: Int,
  val attractedToGender: Int?,
  val description: String,
  val lastSeen: LocalDateTime,
)

@Serializable
data class ProfileConstraintsDTO(
  val minHornLength: Int,
  val maxHornLength: Int,
  val genders: List<String>,
)
