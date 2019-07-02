package praticas.irent

import okhttp3.ResponseBody
import retrofit2.Call
import retrofit2.http.Body
import retrofit2.http.POST

interface Api {
    //AQUI VC VAI COLOCAR O NOMES DA SUA ROTA, USERS.
    @POST("users")
    fun userLogin(@Body request: Request): Call<ResponseBody>

    //Request é um tipo que eu criei na classe Request.kt
    //Call<ResponseBody> significa que a funcao userLogin retorna objeto de qualquer tipo.
    //Nesse caso, vc pode criar uma classe propria sua. Mas essa ai funciona pra qlq tipo, por isso to usando ela, por enquanto.
}