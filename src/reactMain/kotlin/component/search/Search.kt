package component.search

import component.app.client
import component.app.scope
import dto.ProfileDTO
import dto.RangeDTO
import io.ktor.client.call.*
import io.ktor.client.request.*
import kotlinx.coroutines.async
import kotlinx.coroutines.launch
import react.FC
import react.Props
import react.dom.html.ReactHTML.div
import react.useEffectOnce
import react.useState

val Search = FC<Props> {
  var profiles by useState<List<ProfileDTO>>(listOf())
  var ageRange by useState<IntRange?>(null)
  var hornLengthRange by useState<IntRange?>(null)

  useEffectOnce {
    scope.launch {
      val ageRangeJob = async {
        client.get("/constraint/age_range").body<RangeDTO>().toRange()
      }
      val hornLengthJob = async {
        client.get("/constraint/horn_length_range").body<RangeDTO>().toRange()
      }
      ageRange = ageRangeJob.await()
      hornLengthRange = hornLengthJob.await()
    }
  }

  div {
    className = "container justify-content-center"
    if (ageRange != null && hornLengthRange != null) {
      FilterBar {
        filter = ProfileFilter(ageRange!!, hornLengthRange!!, setOf())
        onSearch = {
          scope.launch {
            val profileJob = async {
              client.get("/profile/search"){
                parameter("minAge", it.ageRange.first)
                parameter("maxAge", it.ageRange.last)
                parameter("minHornLength", it.hornLengthRange.first)
                parameter("maxHornLength", it.hornLengthRange.last)
                it.interestedIn.forEach { gender ->
                  parameter("interestedIn", gender)
                }
              }.body<List<ProfileDTO>>()
            }
            profiles = profileJob.await()
          }
        }
      }
    }
    div {
      className = "row"
      profiles.map {
        div {
          className = "col mb-3"
          ProfileCard {
            profile = it
          }
        }
      }
    }
  }
}