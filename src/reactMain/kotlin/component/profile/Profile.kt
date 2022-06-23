package component.profile

import component.app.client
import component.app.scope
import dto.ProfileDTO
import react.dom.html.InputType
import react.dom.html.ReactHTML.div
import react.dom.html.ReactHTML.h1
import react.dom.html.ReactHTML.input
import react.dom.html.ReactHTML.label
import component.profile.ViewMode.*
import dto.PhotoDTO
import io.ktor.client.call.*
import io.ktor.client.request.*
import kotlinx.coroutines.launch
import kotlinx.datetime.LocalDate
import react.*
import react.dom.html.ReactHTML.button
import react.dom.html.ReactHTML.option
import react.dom.html.ReactHTML.select
import react.dom.html.ReactHTML.strong

enum class ViewMode {
  CREATE,
  UPDATE,
  IMMUTABLE
}

data class ProfileConstraints(val ageRange: IntRange, val genders: List<String>)

external interface ProfileProps : Props {
  var errorMessage: String?
  var setErrorMessage: StateSetter<String?>
  var viewMode: ViewMode
  var profileView: ProfileDTO
  var profileConstraints: ProfileConstraints
  var onSubmit: (ProfileDTO) -> Unit
}

private suspend fun fetchPhoto(nickname: String) = client
  .get("/profile/$nickname/photos")
  .body<List<PhotoDTO>>()

val Profile = FC<ProfileProps> { props ->
  var profile by useState(props.profileView)
  val constraints = props.profileConstraints
  var photos by useState<List<PhotoDTO>?>(null)

  useEffect(profile) {
    props.setErrorMessage(null)
  }

  useEffectOnce {
    scope.launch {
      photos = fetchPhoto(profile.nickname)
    }
  }

  val isReadOnly = when (props.viewMode) {
    CREATE -> false
    UPDATE -> false
    IMMUTABLE -> true
  }

  div {
    className = "container justify-content-center"
    div {
      className = "card m-3"
      div {
        className = "card-body"
        div {
          className = "row"
          div {
            className = "col-md-4"
          }
          div {
            className = "col-md-8"
            h1 {
              className = "mb-3"
              +"Profile of ${profile.nickname}"
            }
          }
        }
        div {
          className = "row"
          div {
            className = "col-md-4"

            PhotoCarousel {
              this.photos = photos
            }

            div {
              className = "mb-3"
              label {
                className = "form-label"
                htmlFor = "nickname"
                +"Nickname"
              }
              input {
                readOnly = isReadOnly
                className = if (isReadOnly) "form-control-plaintext" else "form-control"
                id = "nickname"
                value = profile.nickname
                onChange = { profile = profile.copy(nickname = it.target.value) }
              }
            }
          }
          div {
            className = "col-md-8"
            div {
              className = "mb-3"
              label {
                className = "form-label"
                htmlFor = "birthdate"
                +"Birthdate"
              }
              input {
                type = InputType.date
                // Birthdate can only be changed at creation
                when (props.viewMode) {
                  CREATE -> {
                    readOnly = false; className = "form-control"
                  }
                  UPDATE -> {
                    readOnly = true; className = "form-control-plaintext"
                  }
                  IMMUTABLE -> {
                    readOnly = true; className = "form-control-plaintext"
                  }
                }
                value = profile.birthdate.toString()
                id = "birthdate"
                onChange = { profile = profile.copy(birthdate = LocalDate.parse(it.target.value)) }
              }
            }
            div {
              className = "mb-3"
              label {
                className = "form-label"
                htmlFor = "hornLength"
                +"Horn length"
              }
              select {
                className = "form-select w-25"
                id = "hornLength"
                for (index in constraints.ageRange) {
                  option {
                    value = "$index"
                    +"$index"
                  }
                }
                value = "${profile.hornLength}"
                onChange = { profile = profile.copy(hornLength = it.target.value.toInt()) }
              }
            }
            ProfileGenderSelect {
              // specify own gender
              isIncludeDefault = false
              currentGender = profile.gender
              genders = constraints.genders
              this.isReadOnly = isReadOnly
              label = "Gender"
              id = "gender"
              onGenderSelected = { profile = profile.copy(gender = it) }
            }
            ProfileGenderSelect {
              // specify attracted to
              isIncludeDefault = true
              currentGender = profile.attractedToGender
              genders = constraints.genders
              this.isReadOnly = isReadOnly
              label = "Interested in"
              id = "attractedTo"
              onGenderSelected = { profile = profile.copy(attractedToGender = it) }
            }
            ProfileDescription {
              description = profile.description
              this.isReadOnly = isReadOnly
              onChange = { profile = profile.copy(description = it) }
            }
            when (props.viewMode) {
              CREATE -> div {
                props.errorMessage?.let { strong { +it } }
                button {
                  className = "btn btn-outline-light float-end ml-3"
                  +"Create Account"
                  onClick = { props.onSubmit(profile) }
                }
              }
              UPDATE -> div {
                // maybe add edit button to change view mode?
                props.errorMessage?.let { strong { +it } }
                button {
                  className = "btn btn-outline-light float-end ml-3"
                  +"Submit Changes"
                  onClick = { props.onSubmit(profile) }
                }
              }
              IMMUTABLE -> {
                // no button needed
              }
            }
          }
        }
      }
    }
  }
}

