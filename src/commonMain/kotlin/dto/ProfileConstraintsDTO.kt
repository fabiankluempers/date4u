package dto

import kotlinx.serialization.Serializable

@Serializable
data class ProfileConstraintsDTO(
  val minHornLength: Int,
  val maxHornLength: Int,
  val genders: List<String>,
)