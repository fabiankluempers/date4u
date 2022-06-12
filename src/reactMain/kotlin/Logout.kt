import context.AuthContext
import io.ktor.client.request.*
import io.ktor.client.request.forms.*
import io.ktor.client.statement.*
import io.ktor.http.*
import kotlinx.coroutines.launch
import react.FC
import react.Props
import react.dom.html.ReactHTML
import react.dom.html.ReactHTML.button
import react.dom.html.ReactHTML.li
import react.useContext
import util.cookies

private const val LOGOUT_URL = "/perform_logout"

val Logout = FC<Props> {
	li {
		val (_, setAuth) = useContext(AuthContext)
		className = "nav-item"
		button {
			className = "nav-link"
			+"Logout"
			onClick = {
				scope.launch {
					if (client.submitForm(
							url = LOGOUT_URL,
							formParameters = Parameters.build {
								append("_csrf", cookies["XSRF-TOKEN"] ?: "")
							}
						).bodyAsText() == "false"
					) {
						setAuth(false)
					}
				}
			}
		}
	}
}