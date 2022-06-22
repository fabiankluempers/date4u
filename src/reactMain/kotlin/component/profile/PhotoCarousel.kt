package component.profile

import component.generic.LoadingSpinner
import dto.PhotoDTO
import react.FC
import react.Props
import react.dom.aria.ariaHidden
import react.dom.html.ButtonType
import react.dom.html.ReactHTML.button
import react.dom.html.ReactHTML.div
import react.dom.html.ReactHTML.img
import react.dom.html.ReactHTML.span

interface PhotoCarouselProps : Props {
  var photos : List<PhotoDTO>?
}

val PhotoCarousel = FC<PhotoCarouselProps> { props ->
  div {
    id = "photoCarousel"
    className = "carousel slide mb-3"
    asDynamic()["data-bs-ride"] = "carousel"
    div {
      className = "carousel-inner"
      props.photos?.map { (nickname, name, isProfilePhoto) ->
        div {
          className = "carousel-item"
          if(isProfilePhoto) className += " active"
          img {
            src = "/photo/${name}.jpg"
            className = "d-block w-100 rounded"
            alt = "A photo of ${nickname}."
            if (isProfilePhoto) {
              alt = "The profile photo of ${nickname}."
            }
          }
        }
      } ?: div {
        className = "carousel-item active"
        div {
          className = "d-flex justify-content-center"
          div {
            className = "mx-3"
            + "Loading Photos..."
          }
          LoadingSpinner()
        }
      }
    }
    button {
      className = "carousel-control-prev"
      type = ButtonType.button
      asDynamic()["data-bs-target"] = "#photoCarousel"
      asDynamic()["data-bs-slide"] = "prev"
      span {
        className = "carousel-control-prev-icon"
        ariaHidden = true
      }
      span {
        className = "visually-hidden"
        + "Previous"
      }
    }
    button {
      className = "carousel-control-next"
      type = ButtonType.button
      asDynamic()["data-bs-target"] = "#photoCarousel"
      asDynamic()["data-bs-slide"] = "next"
      span {
        className = "carousel-control-next-icon"
        ariaHidden = true
      }
      span {
        className = "visually-hidden"
        + "Next"
      }
    }
  }
}