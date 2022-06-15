import dto.ProfileConstraintsDTO
import dto.ProfileDTO
import io.ktor.client.call.*
import io.ktor.client.request.*
import kotlinx.coroutines.async
import kotlinx.coroutines.launch
import react.FC
import react.Props
import react.useEffectOnce
import react.useState

val MyProfile = FC<Props> {
    val (profile, setProfile) = useState<ProfileDTO?>(null)
    val (constraints, setConstraints) = useState<ProfileConstraintsDTO?>(null)
    useEffectOnce {
        println("fetching")
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
            onSubmit = { println("submitted") }
        }
    }
}

val fetchProfileConstraints = suspend {
    client.get("/profile/constraints").body<ProfileConstraintsDTO>()
}

val fetchMyProfile = suspend {
    client.get("/profile").body<ProfileDTO>()
}


