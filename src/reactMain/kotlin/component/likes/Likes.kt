package component.likes

import react.FC
import react.Props

interface LikesProps : Props {
  var likesViewMode : LikesViewMode
}

enum class LikesViewMode {
  LIKES,
  LIKERS,
}

val Likes = FC<Props> {

}