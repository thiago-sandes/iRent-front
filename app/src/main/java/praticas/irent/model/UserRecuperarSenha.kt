package praticas.irent.model

import com.google.gson.annotations.SerializedName

data class UserRecuperarSenha(
    @SerializedName("email")
    val email: String? = ""
)

