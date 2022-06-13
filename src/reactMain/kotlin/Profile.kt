import dto.ProfileDTO
import io.ktor.client.call.*
import io.ktor.client.request.*
import kotlinx.coroutines.async
import kotlinx.coroutines.launch
import react.FC
import react.Props
import react.useEffectOnce
import react.useState

external interface ProfileProps : Props {
	val isEditable : Boolean
	val profile: ProfileDTO
}

val Profile = FC<ProfileProps> {

}
