package component.search

import component.app.client
import component.app.scope
import component.generic.LoadingSpinner
import csstype.rem
import dto.PhotoDTO
import dto.ProfileDTO
import io.ktor.client.call.*
import io.ktor.client.request.*
import kotlinx.coroutines.launch
import react.*
import react.css.css
import react.dom.html.ReactHTML.div
import react.dom.html.ReactHTML.h5
import react.dom.html.ReactHTML.img
import react.dom.html.ReactHTML.p
import react.router.dom.NavLink

interface ProfileCardProps : Props {
  var profile: ProfileDTO
}

val ProfileCard = FC<ProfileCardProps> { props ->
  var profilePic by useState<PhotoDTO?>(null)
  useEffect(props) {
    scope.launch {
      profilePic = client
        .get("profile/${props.profile.nickname}/profile_photo")
        .body<PhotoDTO?>()
    }
  }
  NavLink {
    to = "/profile/${props.profile.nickname}"
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