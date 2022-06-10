import kotlinext.js.jso
import kotlinx.coroutines.MainScope
import react.FC
import react.Props
import react.router.dom.BrowserRouter

val scope = MainScope()

val Application = FC<Props> {
    AuthContext.Provider {
        value =  jso { authenticated = false }
        BrowserRouter {
            NavBar()
            MyRoutes()
        }
    }

}
