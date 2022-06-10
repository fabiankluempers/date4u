import react.FC
import react.Props
import react.create
import react.dom.aria.ariaControls
import react.dom.aria.ariaExpanded
import react.dom.aria.ariaLabel
import react.dom.html.ButtonType
import react.dom.html.ReactHTML.button
import react.dom.html.ReactHTML.div
import react.dom.html.ReactHTML.li
import react.dom.html.ReactHTML.nav
import react.dom.html.ReactHTML.span
import react.dom.html.ReactHTML.ul
import react.router.dom.Link
import react.router.dom.NavLink

val NavBar = FC<Props> {
    nav {
        className = "navbar navbar-expand-lg bg-light"
        div {
            className = "container-fluid"
            NavLink {
                className = "navbar-brand"
                to = "/"
                +"Navbar"
            }
            button {
                className = "navbar-toggler"
                type = ButtonType.button
                asDynamic()["data-bs-toggle"] = "collapse"
                asDynamic()["data-bs-target"] = "#navbarSupportedContent"
                ariaControls = "navbarSupportedContent"
                ariaExpanded = false
                ariaLabel = "Toggle navigation"

                span { className = "navbar-toggler-icon" }
            }
            div {
                className = "collapse navbar-collapse"
                id = "navbarSupportedContent"
                ul {
                    className = "navbar-nav me-auto mb-2 mb-lg-0"
                    SimpleNavItem {
                        label = "My Profile"
                        to = "/my_profile"
                    }
                    SimpleNavItem {
                        label = "Recommended Unicorns"
                        to = "/recommended_profiles"
                    }
                    SimpleNavItem {
                        label = "Login"
                        to = "/login"
                    }
                }
            }
        }
    }
}

private interface SimpleNavItemProps : Props {
    var label : String
    var to : String
}

private val SimpleNavItem = FC<SimpleNavItemProps> {
    li {
        className = "nav-item"
        NavLink {
            className = "nav-link"
            to = it.to
            + it.label
        }
    }
}
