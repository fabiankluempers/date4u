package util

import component.app.client
import dto.ProfileDTO
import dto.RangeDTO
import io.ktor.client.*
import io.ktor.client.call.*
import io.ktor.client.request.*
import io.ktor.http.*

suspend fun HttpClient.fetchHornLengthRange() = get("/constraint/horn_length_range").body<RangeDTO>().toRange()

suspend fun HttpClient.fetchGenders() = get("/constraint/genders").body<List<String>>()

suspend fun HttpClient.fetchAgeRange() = get("/constraint/age_range").body<RangeDTO>().toRange()

suspend fun HttpClient.saveProfile(profileDTO: ProfileDTO) =
  put("/profile") {
    addXsrfToken()
    contentType(ContentType.Application.Json)
    setBody(profileDTO)
  }

suspend fun HttpClient.fetchMyProfile() = get("/profile").body<ProfileDTO>()

suspend fun HttpClient.fetchProfile(nickname: String) = get("/profile/$nickname").body<ProfileDTO>()