package dto

import kotlinx.serialization.Serializable

//TODO (re) introduce type param. For some reason typed classes cant be unmarshalled in FE.
@Serializable
data class ResponseDTO private constructor(private val success: ProfileDTO?, private val error: ErrorDTO?) {
  fun isError() = error != null

  fun get() = success!!
  fun getError() = error!!
  fun isSuccess() = success != null

  companion object {
	fun success(success: ProfileDTO) = ResponseDTO(success, null)

	fun failure(error: ErrorDTO) = ResponseDTO(null, error)
  }
}