package praticas.irent.webservice


import okhttp3.OkHttpClient
import praticas.irent.model.UserRecuperarSenha
import retrofit2.Call
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.Field
import retrofit2.http.FormUrlEncoded
import retrofit2.http.POST
import java.util.concurrent.TimeUnit


//const val BASE_URL = "http://young-lake-11756.herokuapp.com"
const val RECUPERAR_SENHA_PATH = "passwords"

interface RecuperarSenhaWebSetvice {

    @FormUrlEncoded
    @POST("$BASE_URL/$RECUPERAR_SENHA_PATH")
    fun recuperarSenha(
        @Field("email") email: String
    ): Call<UserRecuperarSenha>

}

fun createUserOkhttpCliente() = OkHttpClient.Builder()
    .connectTimeout(30, TimeUnit.SECONDS)
    .readTimeout(30, TimeUnit.SECONDS)
    .writeTimeout(30, TimeUnit.SECONDS)
    .build()

fun postRecuperarSenha() = Retrofit.Builder()
    .baseUrl(BASE_URL)
    .client(createUserOkhttpCliente())
    .addConverterFactory(GsonConverterFactory.create())
    .build()
    .create(RecuperarSenhaWebSetvice::class.java)
