package praticas.irent.model

import com.google.gson.Gson

data class Usuario(val email: String, val fone: String) {

    fun toJson(): String {
        return Gson().toJson(this)
    }

    companion object {
        fun fromJson(json: String): Usuario {
            return Gson().fromJson<Usuario>(json, Usuario::class.java)
        }
    }
}

fun main() {
//    println(Usuario("teste@test.com", "897373830").toJson())
    print(Usuario.fromJson("{\"email\":\"teste@test.com\",\"fone\":\"897373830\"}").toString())
}

