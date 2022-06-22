package component.app

import kotlinx.browser.document
import react.create
import react.dom.render

fun main() {
    render(Application.create(), document.getElementById("root")!!)
}
