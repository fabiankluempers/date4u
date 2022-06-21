package component.search

import client
import dto.ProfileDTO
import io.ktor.client.call.*
import io.ktor.client.request.*
import kotlinx.coroutines.launch
import react.FC
import react.Props
import react.dom.html.ReactHTML.div
import react.dom.html.ReactHTML.ul
import react.useEffectOnce
import react.useState
import scope

val Search = FC<Props> {
  var profiles by useState<List<ProfileDTO>>(listOf())

  useEffectOnce {
	scope.launch {
	  profiles = client.get("/profile/all").body()
	}
  }

  div {
	className = "container justify-content-center"
	FilterBar()
	div {
	  className = "row"
	  profiles.map {
		div {
		  className = "col"
		  ProfileCard {
			profile = it
		  }
		}
	  }
	}
  }
}