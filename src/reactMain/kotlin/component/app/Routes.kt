package component.app

import component.auth.Login
import component.auth.Logout
import Welcome
import component.profile.MyProfile
import component.profile.OtherProfile
import component.search.Search
import react.FC
import react.Props
import react.create
import react.createElement
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
            element = createElement(Logout)
        }
        Route {
            path = "/my_profile"
            element = createElement(MyProfile)
        }
        Route {
            path = "/search_profiles"
            element = createElement(Search)
        }
        Route {
            path = "/profile/:nickname"
            element = createElement(OtherProfile)
        }
    }
}
