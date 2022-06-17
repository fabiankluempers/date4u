import context.AuthContext
import dto.LoginState
import io.ktor.client.call.*
import io.ktor.client.request.*
import kotlinx.coroutines.launch
import react.*
import react.dom.html.ReactHTML.div
import react.dom.html.ReactHTML.strong
import react.router.dom.NavLink
import util.COOKIES
import util.addXsrfToken

private const val LOGOUT_URL = "/perform_logout"

val Logout = FC<Props> {
	var isLoggedOut by useState(false)
	val (_, setAuth) = useContext(AuthContext)

	useEffectOnce {
		scope.launch {
			val loginState : LoginState = client.post {
				url(LOGOUT_URL)
				addXsrfToken()
			}.body()
			if (!loginState.isLoggedIn) {
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
