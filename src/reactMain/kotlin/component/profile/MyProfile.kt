package component.profile

import component.app.client
import component.app.scope
import dto.ProfileConstraintsDTO
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
import util.addXsrfToken

val MyProfile = FC<Props> {
  val (profile, setProfile) = useState<ProfileDTO?>(null)
  val (constraints, setConstraints) = useState<ProfileConstraintsDTO?>(null)
  val (err, setErr) = useState<String?>(null)
  val navigate = useNavigate()
  useEffectOnce {
    scope.launch {
      val profileJob = async { fetchMyProfile() }
      val constraintJob = async { fetchProfileConstraints() }
      setProfile(profileJob.await())
      setConstraints(constraintJob.await())
    }
  }
  if (profile != null && constraints != null) {
    Profile {
      viewMode = ViewMode.UPDATE
      profileView = profile
      profileConstraints = constraints
      errorMessage = err
      setErrorMessage = setErr
      onSubmit = {
        scope.launch {
          val response : ResponseDTO = saveProfile(it).body()
          if (response.isError()) setErr(response.getError().detail ?: "An unknown Error occurred")
          else setProfile(response.get())
        }
      }
    }
  }
}

suspend fun saveProfile(profileDTO: ProfileDTO) = client
  .put("/profile") {
    addXsrfToken()
    contentType(ContentType.Application.Json)
    setBody(profileDTO)
  }

val fetchProfileConstraints = suspend {
  client.get("/profile/constraints").body<ProfileConstraintsDTO>()
}

val fetchMyProfile = suspend {
  client.get("/profile").body<ProfileDTO>()
}


