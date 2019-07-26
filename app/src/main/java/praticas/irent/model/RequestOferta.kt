package praticas.irent.model

class RequestOferta (
    val user_id: Int,
    val endereco_id : Int? = null,
    val titulo : String,
    val telefone: String,
    val descricao : String,
    val preco : String,
    val restricao : String
)