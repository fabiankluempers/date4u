package util

import kotlinx.browser.document

private val getCookies = {
	document.cookie
		.split(';')
		.associate { with(it.split('=')) { component1().trim() to component2() } }
}

object COOKIES {
	operator fun get(key: String) = getCookies()[key]
}

