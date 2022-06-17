package dto

@kotlinx.serialization.Serializable
data class RestError(val error: String, val message: String, val detail: String)
