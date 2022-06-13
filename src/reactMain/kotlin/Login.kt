import context.AuthContext
import io.ktor.client.request.forms.*
import io.ktor.client.statement.*
import io.ktor.http.*
import kotlinx.coroutines.*
import react.*
import react.dom.html.ButtonType
import react.dom.html.InputType
import react.dom.html.ReactHTML.button
import react.dom.html.ReactHTML.form
import react.dom.html.ReactHTML.h1
import react.dom.html.ReactHTML.input
import react.dom.html.ReactHTML.label
import react.router.useNavigate
import util.COOKIES

private const val LOGIN_URL = "/perform_login"

val Login = FC<Props> {
	var username by useState("")
	var password by useState("")
	var err by useState("")
	val auth = useContext(AuthContext)
	val navigate = useNavigate()

	useEffect(username, password) { err = "" }

	form {
		onSubmit = {
			it.preventDefault()
		}
		h1 {
			+"Sign In"
		}
		input {
			type = InputType.text
			id = "username"
			name = "username"
			onChange = { username = it.target.value }
			value = username
		}
		label {
			htmlFor = "username"
			+"Username"
		}
		input {
			type = InputType.password
			id = "password"
			name = "password"
			onChange = { password = it.target.value }
			value = password
		}
		label {
			htmlFor = "password"
			+"Password"
		}
		if (err.isNotBlank()) {
			+err
		}
		button {
			type = ButtonType.button
			+"Submit"
			onClick = {
				scope.launch {
					// TODO remove bodyAsText for useful type
					val response = client.submitForm(
						url = LOGIN_URL,
						formParameters = Parameters.build {
							append("username", username)
							append("password", password)
							append("_csrf", COOKIES["XSRF-TOKEN"] ?: "")
						}).bodyAsText()
					if (response == "true") {
						auth.setAuthenticated(true)
						navigate("/")
					} else {
						err = "Wrong username or password."
					}
				}
			}
		}
	}
}
