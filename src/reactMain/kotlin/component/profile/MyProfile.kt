package component.profile

import component.app.client
import component.app.scope
import dto.ProfileDTO
import dto.ResponseDTO
import io.ktor.client.call.*
import io.ktor.client.request.*
import io.ktor.http.*
import kotlinx.coroutines.async
import kotlinx.coroutines.launch
import react.FC
import react.Props
import react.router.useNavigate
import react.useEffectOnce
import react.useState
import util.*

val MyProfile = FC<Props> {
  var profile by useState<ProfileDTO?>(null)
  var constraints by useState<ProfileConstraints?>(null)
  val (err, setErr) = useState<String?>(null)

  useEffectOnce {
    scope.launch {
      val profileJob = async { client.fetchMyProfile() }
      val ageRangeJob = async { client.fetchAgeRange() }
      val genderJob = async { client.fetchGenders() }
      profile = profileJob.await()
      constraints = ProfileConstraints(ageRangeJob.await(), genderJob.await())
    }
  }
  if (profile != null && constraints != null) {
    Profile {
      viewMode = ViewMode.UPDATE
      profileView = profile!!
      profileConstraints = constraints!!
      errorMessage = err
      setErrorMessage = setErr
      onSubmit = {
        scope.launch {
          val response: ResponseDTO = client.saveProfile(it).body()
          if (response.isError()) setErr(response.getError().detail ?: "An unknown Error occurred")
          else profile = response.get()
        }
      }
    }
  }
}



