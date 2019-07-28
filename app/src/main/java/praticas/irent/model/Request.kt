package praticas.irent.model

import com.google.gson.annotations.SerializedName

class Request(val email: String, val password: String)

class UserRecuperarSenha(
    @SerializedName("email")
    val email: String? = ""
)