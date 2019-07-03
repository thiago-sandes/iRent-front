package praticas.irent.model

data class TokenResponse(var bearer: String, var token: String, var refreshToken: String)

data class TokenResponseRecuperarSenha(var email: String)