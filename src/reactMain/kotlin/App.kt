import kotlinx.browser.document
import kotlinx.coroutines.MainScope
import react.FC
import react.Props
import react.dom.html.ReactHTML.button
import react.router.dom.BrowserRouter
import util.cookies

val scope = MainScope()

val Application = FC<Props> {
    BrowserRouter {
        NavBar()
        MyRoutes()
    }
}
