import react.FC
import react.Props
import react.create
import react.createElement
import react.dom.html.ReactHTML.div
import react.router.Route
import react.router.Routes

val MyRoutes = FC<Props> {
    Routes {
        Route {
            index = true
            element = Welcome.create {
                name = "test"
            }
        }
        Route {
            path = "/login"
            element = createElement(Login)
        }
        Route {
            path = "/logout"
        }
    }
}
