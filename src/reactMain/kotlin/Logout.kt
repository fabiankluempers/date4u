import context.AuthContext
import io.ktor.client.request.*
import io.ktor.client.statement.*
import io.ktor.http.*
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import react.*
import react.dom.html.ReactHTML.div
import react.dom.html.ReactHTML.strong
import react.router.dom.NavLink
import util.COOKIES

private const val LOGOUT_URL = "/perform_logout"

val Logout = FC<Props> {
	var isLoggedOut by useState(false)
	val (_, setAuth) = useContext(AuthContext)

	useEffectOnce {
		scope.launch {
			delay(3000L)
			if (client.post {
					url(LOGOUT_URL)
					headers {
						append("X-XSRF-TOKEN", COOKIES["XSRF-TOKEN"] ?: "")
					}
				}.bodyAsText() == "false"
			) {
				setAuth(false)
				isLoggedOut = true
			}
		}
	}

	if (!isLoggedOut) {
		div {
			strong {
				+ "Logging out... "
			}
			div {
				className="spinner-border spinner-border-sm ms-auto"
			}
		}
	} else {
		div {
			strong {
				+ "Logged out successfully, return to "
			}
			NavLink {
				className = "nav-lin"
				to = "/"
				+ "home"
			}
			strong {
				+ "?"
			}
		}
	}
}