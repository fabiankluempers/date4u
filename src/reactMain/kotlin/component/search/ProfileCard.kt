package component.search

import client
import component.LoadingSpinner
import csstype.Resize
import csstype.rem
import dto.PhotoDTO
import dto.ProfileDTO
import io.ktor.client.call.*
import io.ktor.client.request.*
import kotlinx.coroutines.launch
import react.FC
import react.Props
import react.css.css
import react.dom.html.ReactHTML.div
import react.dom.html.ReactHTML.h5
import react.dom.html.ReactHTML.img
import react.dom.html.ReactHTML.p
import react.dom.html.ReactHTML.textarea
import react.useEffectOnce
import react.useState
import scope

interface ProfileCardProps : Props {
  var profile: ProfileDTO
}

val ProfileCard = FC<ProfileCardProps> { props ->
  var profilePic by useState<PhotoDTO?>(null)
  useEffectOnce {
    scope.launch {
      profilePic = client
        .get("profile/${props.profile.nickname}/profile_photo")
        .body<PhotoDTO?>()
    }
  }
  div {
    css { width = 18.rem }
    className += " card"
    if (profilePic != null) {
      img {
        src = "photo/${profilePic!!.name}.jpg"
        className = "card-img-top"
        alt = "Profile photo of ${props.profile.nickname}."
      }
    } else {
      LoadingSpinner()
    }
    div {
      className = "card-body"
      h5 {
        className = "card-title"
        +props.profile.nickname
      }
      p {
        className = "card-text"
        +"Birthdate: ${props.profile.birthdate}"
      }
      p {
        className = "card-text"
        +"Hornlength: ${props.profile.hornLength}"
      }
    }
  }
}