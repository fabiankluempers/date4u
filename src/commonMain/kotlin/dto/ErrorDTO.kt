package dto

import kotlinx.serialization.Serializable

@Serializable
data class ErrorDTO(val error: String, val message: String? = null, val detail: String? = null)
