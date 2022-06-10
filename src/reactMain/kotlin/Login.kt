import io.ktor.client.*
import io.ktor.client.engine.js.*
import io.ktor.client.request.*
import io.ktor.client.request.forms.*
import io.ktor.http.*
import io.ktor.util.reflect.*
import kotlinx.coroutines.*
import react.FC
import react.Props
import react.dom.html.ButtonType
import react.dom.html.InputType
import react.dom.html.ReactHTML
import react.dom.html.ReactHTML.button
import react.dom.html.ReactHTML.form
import react.dom.html.ReactHTML.h1
import react.dom.html.ReactHTML.h2
import react.dom.html.ReactHTML.input
import react.dom.html.ReactHTML.label
import react.useState
import util.cookies

private const val LOGIN_URL = "/perform_login"

val Login = FC<Props> {
    var username by useState("")
    var password by useState("")

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
        input {
            hidden = true
            type = InputType.text
            name = "_csrf"
            value = cookies["XSRF-TOKEN"] ?: ""
        }
        button {
            type = ButtonType.button
            +"Submit"
            onClick = {
                scope.launch {
                    val client = HttpClient(Js)
                    client.submitForm(
                        url = LOGIN_URL,
                        formParameters = Parameters.build {
                            append("username", username)
                            append("password", password)
                        })
                }
            }
        }
    }
}
