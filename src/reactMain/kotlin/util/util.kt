package util

import kotlinx.browser.document
import react.FC
import react.Props
import react.ReactElement

val cookies = document.cookie
    .split(';')
    .associate { with(it.split('=')) { component1().trim() to component2() } }

