package dto

import kotlinx.serialization.Serializable

@Serializable
class LoginState(val isLoggedIn : Boolean)