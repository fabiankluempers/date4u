package context

import react.createContext

data class AuthState(
	val isAuthenticated: Boolean,
	val setAuthenticated: (Boolean) -> Unit
)

val AuthContext = createContext<AuthState>()