import dto.ProfileDTO
import io.ktor.client.call.*
import io.ktor.client.request.*
import kotlinx.coroutines.async
import kotlinx.coroutines.launch
import react.FC
import react.Props
import react.dom.html.ReactHTML.div
import react.dom.html.ReactHTML.h1
import react.dom.html.ReactHTML.input
import react.dom.html.ReactHTML.label
import react.useEffectOnce
import react.useState

enum class ViewMode {
    CREATE,
    UPDATE,
    IMMUTABLE
}

external interface ProfileProps : Props {
    var viewMode: ViewMode
    var profile: ProfileDTO
    var onSubmit: (ProfileDTO) -> Unit
}

val Profile = FC<ProfileProps> { props ->
    var profile by useState(props.profile)
    div {
        className = "container justify-content-center"
        div {
            className = "row"
            div {
                className = "col-md-4"
            }
            div {
                className = "col-md-8"
                h1 {
                    + "Profile of ${profile.nickname}"
                }
            }
        }
        div {
            className = "row"
            div {
                className = "col-md-4"
                //TODO Photos here
                label {
                    className = "form-label"
                    htmlFor = "nickname"
                    + "Nickname"
                }
                input {
                    when (props.viewMode) {
                        ViewMode.CREATE -> {
                            className = "form-control"
                        }
                        ViewMode.UPDATE -> {
                            className = "form-control"
                        }
                        ViewMode.IMMUTABLE -> {
                            className = "form-control-plaintext"
                            readOnly = true
                        }
                    }
                    id = "nickname"
                    value = profile.nickname
                    onChange = { profile = profile.copy(nickname = it.target.value) }
                }
            }
            div {
                className = "col-md-8"

            }
        }
    }
}
