import component.profile.Profile
import component.profile.ViewMode
import dto.ProfileConstraintsDTO
import dto.ProfileDTO
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

val MyProfile = FC<Props> {
  val (profile, setProfile) = useState<ProfileDTO?>(null)
  val (constraints, setConstraints) = useState<ProfileConstraintsDTO?>(null)
  var err by useState<String?>(null)
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
	  onSubmit = {
		scope.launch {
		  val response = saveProfile(it)
		  if (response.status.value in 200..299) {
			navigate("/my_profile")
		  } else {
			err = response.headers["error-message"] ?: "An unknown error occurred"
		  }
		}
	  }
	}
  }
}

suspend fun saveProfile(profileDTO: ProfileDTO) = client
  .post("/profile") {
	contentType(ContentType.Application.Json)
	setBody(profileDTO)
  }

val fetchProfileConstraints = suspend {
  client.get("/profile/constraints").body<ProfileConstraintsDTO>()
}

val fetchMyProfile = suspend {
  client.get("/profile").body<ProfileDTO>()
}


