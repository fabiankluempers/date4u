package util

import io.ktor.client.request.*
import kotlinx.browser.document

private val getCookies = {
	document.cookie
		.split(';')
		.associate { with(it.split('=')) { component1().trim() to component2() } }
}

object COOKIES {
	operator fun get(key: String) = getCookies()[key]
}

fun HttpRequestBuilder.addXsrfToken() {
	headers["X-XSRF-TOKEN"] = COOKIES["XSRF-TOKEN"] ?: error("Missing XSRF-TOKEN cookie")
}

