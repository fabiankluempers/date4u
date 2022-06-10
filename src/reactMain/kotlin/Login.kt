import io.ktor.client.*
import io.ktor.client.engine.js.*
import io.ktor.client.request.forms.*
import io.ktor.client.statement.*
import io.ktor.http.*
import kotlinx.coroutines.*
import react.FC
import react.Props
import react.dom.html.ButtonType
import react.dom.html.InputType
import react.dom.html.ReactHTML.button
import react.dom.html.ReactHTML.form
import react.dom.html.ReactHTML.h1
import react.dom.html.ReactHTML.input
import react.dom.html.ReactHTML.label
import react.router.useNavigate
import react.useContext
import react.useState
import util.cookies

private const val LOGIN_URL = "/perform_login"

val Login = FC<Props> {
    var username by useState("")
    var password by useState("")
    var err by useState("")
    val auth = useContext(AuthContext)
    val navigate = useNavigate()

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
            onChange = { username = it.target.value; err = "" }
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
            onChange = { password = it.target.value; err = "" }
            value = password
        }
        label {
            htmlFor = "password"
            +"Password"
        }
        input {
            hidden = true
            type = InputType.text
            name = "_csrf"
            readOnly = true
            value = cookies["XSRF-TOKEN"] ?: ""
        }
        if (err.isNotBlank()) {
            +err
        }
        button {
            type = ButtonType.button
            +"Submit"
            onClick = {
                scope.launch {
                    val client = HttpClient(Js)
                    // TODO remove bodyAsText for useful type
                    // TODO make client global
                    val response = client.submitForm(
                        url = LOGIN_URL,
                        formParameters = Parameters.build {
                            append("username", username)
                            append("password", password)
                            append("_csrf", cookies["XSRF-TOKEN"] ?: "")
                        }).bodyAsText()
                    if (response == "true") {
                        auth.authenticated = true
                        navigate("/")
                    } else {
                        err = "Wrong username or password."
                    }
                }
            }
        }

        if (auth.authenticated) navigate("/")
    }
}
