import dto.ProfileDTO
import io.ktor.client.call.*
import io.ktor.client.request.*
import kotlinx.coroutines.launch
import react.FC
import react.Props
import react.useEffectOnce
import react.useState

val MyProfile = FC<Props> {
    var profileState by useState<ProfileDTO?>(null)
    useEffectOnce {
        println("fetching")
        scope.launch {
            val fetchRes = fetchMyProfile()
            println(fetchRes)
            profileState = fetchRes
        }
    }
    profileState?.let {
        println("rendering $it")
        Profile {
            viewMode = ViewMode.UPDATE
            profile = it
            onSubmit = { println("submitted") }
        }
    }
}

val fetchMyProfile = suspend {
    client.get("/myProfile").body<ProfileDTO>()
}
