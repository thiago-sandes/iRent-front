package praticas.irent.model

data class TokenResponse(var bearer: String, var token: String, var refreshToken: String)
