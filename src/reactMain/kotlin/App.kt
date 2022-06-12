import context.AuthContext
import context.AuthState
import io.ktor.client.*
import io.ktor.client.engine.js.*
import kotlinx.coroutines.MainScope
import react.FC
import react.Props
import react.router.dom.BrowserRouter
import react.useState

val scope = MainScope()
val client = HttpClient(Js)

val Application = FC<Props> {
    AuthContext.Provider {
        var authState by useState(false)
        value = AuthState(authState) { authState = it }
        BrowserRouter {
            NavBar()
            MyRoutes()
        }
    }
}
