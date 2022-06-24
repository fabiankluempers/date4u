package component.profile

import component.app.client
import component.app.scope
import dto.ProfileDTO
import kotlinx.coroutines.launch
import react.*
import react.dom.html.ReactHTML.div
import react.router.useParams
import util.fetchProfile

val OtherProfile = FC<Props> {
  val nickname = useParams()["nickname"]
  var profile by useState<ProfileDTO?>(null)
  val (_, setError) = useState<String?>(null)
  useEffectOnce {
    nickname?.let {
      scope.launch {
        profile = client.fetchProfile(it)
      }
    }
  }
  if (profile != null) {
    Profile {
      profileView = profile!!
      profileConstraints = ProfileConstraints(ageRange = 0..100, listOf("Female", "Male", "Diverse"))
      viewMode = ViewMode.IMMUTABLE
      setErrorMessage = setError
    }
  }
}