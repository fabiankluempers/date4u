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

private data class Constraints(val ageRange: IntRange, val hornLengthRange: IntRange, val genders: List<String>)

data class ProfileFilter(val ageRange: IntRange, val hornLengthRange: IntRange, val interestedIn: Set<Int>)

val Search = FC<Props> {
  var profiles by useState<List<ProfileDTO>>(listOf())
  var constraints by useState<Constraints?>(null)

  useEffectOnce {
    scope.launch {
      val ageRangeJob = async {
        client.get("/constraint/age_range").body<RangeDTO>().toRange()
      }
      val hornLengthJob = async {
        client.get("/constraint/horn_length_range").body<RangeDTO>().toRange()
      }
      val genderJob = async {
        client.get("/constraint/genders").body<List<String>>()
      }
      constraints = Constraints(ageRangeJob.await(), hornLengthJob.await(), genderJob.await())
    }
  }

  div {
    className = "container justify-content-center"
    constraints?.run {
      FilterBar {
        filter = ProfileFilter(ageRange, hornLengthRange, setOf())
        this@FilterBar.genders = this@run.genders
        onSearch = { scope.launch { profiles = it.fetchSearchResults() } }
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

private suspend fun ProfileFilter.fetchSearchResults() =
  client.get("/search") {
    parameter("minAge", ageRange.first)
    parameter("maxAge", ageRange.last)
    parameter("minHornLength", hornLengthRange.first)
    parameter("maxHornLength", hornLengthRange.last)
    interestedIn.forEach { gender ->
      parameter("interestedIn", gender)
    }
  }.body<List<ProfileDTO>>()
