package component

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
  println(props.photos)
  div {
    id = "photoCarousel"
    className = "carousel slide"
    asDynamic()["data-bs-ride"] = "carousel"
    div {
      className = "carousel-inner"
      props.photos?.map {
        div {
          className = "carousel-item"
          img {
            src = "/photo/${it.name}.jpg"
            className = "d-block w-100"
            alt = "A photo of ${it.nickname}."
            if (it.isProfilePhoto) {
              alt = "The profile photo of ${it.nickname}."
              className += " active"
            }
          }
        }
      } ?: LoadingSpinner()
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