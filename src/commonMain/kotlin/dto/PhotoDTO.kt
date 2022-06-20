package dto

import kotlinx.datetime.LocalDateTime

@kotlinx.serialization.Serializable
data class PhotoDTO(val nickname: String, val name: String, val isProfilePhoto: Boolean, val created: LocalDateTime)
