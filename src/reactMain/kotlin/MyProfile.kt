import dto.ProfileDTO
import io.ktor.client.call.*
import io.ktor.client.request.*
import kotlinx.coroutines.launch
import react.FC
import react.Props
import react.useEffectOnce
import react.useState

val MyProfile = FC<Props> {
	var profile by useState<ProfileDTO?>(null)
	useEffectOnce {
		scope.launch {
			profile = fetchMyProfile()
		}
	}
}

val fetchMyProfile = suspend {
	client.get("/myProfile").body() as ProfileDTO
}