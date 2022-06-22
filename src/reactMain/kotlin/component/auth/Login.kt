package component.auth

import component.app.client
import component.app.scope
import context.AuthContext
import dto.LoginState
import io.ktor.client.call.*
import io.ktor.client.request.forms.*
import io.ktor.http.*
import kotlinx.coroutines.*
import react.*
import react.dom.html.ButtonType
import react.dom.html.InputType
import react.dom.html.ReactHTML.button
import react.dom.html.ReactHTML.div
import react.dom.html.ReactHTML.form
import react.dom.html.ReactHTML.h1
import react.dom.html.ReactHTML.input
import react.dom.html.ReactHTML.label
import react.router.useNavigate
import util.addXsrfToken

private const val LOGIN_URL = "/perform_login"

val Login = FC<Props> {
  var username by useState("")
  var password by useState("")
  var err by useState("")
  val auth = useContext(AuthContext)
  val navigate = useNavigate()

  useEffect(username, password) { err = "" }

  div {
	className = "d-flex justify-content-center"
	div {
	  className = "card m-3"
	  div {
		className = "card-body"
		h1 {
		  className = "card-title"
		  +"Sign In"
		}
		form {
		  className = "m-3"
		  onSubmit = {
			it.preventDefault()
		  }
		  div {
			className = "mb-3"
			label {
			  className = "form-label"
			  htmlFor = "email"
			  +"Email Address"
			}
			input {
			  className = "form-control"
			  type = InputType.email
			  id = "email"
			  name = "email"
			  placeholder = "name@example"
			  onChange = { username = it.target.value }
			  value = username
			}
		  }
		  div {
			className = "mb-3"
			label {
			  className = "form-label"
			  htmlFor = "password"
			  +"Password"
			}
			input {
			  className = "form-control"
			  type = InputType.password
			  id = "password"
			  name = "password"
			  onChange = { password = it.target.value }
			  value = password
			}
		  }
		  if (err.isNotBlank()) {
			+err
		  }
		  button {
			className = "btn btn-outline-light float-end"
			type = ButtonType.button
			+"Submit"
			onClick = {
			  scope.launch {
				// TODO remove bodyAsText for useful type
				val authState: LoginState = client.submitForm(
				  url = LOGIN_URL,
				  formParameters = Parameters.build {
					append("username", username)
					append("password", password)
				  }
				) {
				  addXsrfToken()
				}.body()
				if (authState.isLoggedIn) {
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
	}
  }
}
