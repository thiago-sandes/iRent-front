package praticas.irent.webservice

import okhttp3.OkHttpClient
//import okhttp3.logging.HttpLoggingInterceptor
import praticas.irent.model.Request
import praticas.irent.model.TokenResponse
import praticas.irent.model.TokenResponseRecuperarSenha
//import praticas.irent.model.TokenResponseRecuperarSenha
import praticas.irent.model.UserRecuperarSenha
import retrofit2.Call
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.Body
import retrofit2.http.POST
import java.util.concurrent.TimeUnit

const val RECUPERAR_SENHA_PATH = "passwords"

interface UserWebService {

    @POST("sessions")
    fun userLogin(@Body request: Request): Call<TokenResponse>

    @POST("users")
    fun userSignUp(@Body request: Request): Call<TokenResponse>

    @POST("passwords")
    fun recuperarSenha(@Body request: UserRecuperarSenha): Call<TokenResponseRecuperarSenha>
}


const val BASE_URL = "https://young-lake-11756.herokuapp.com/"

fun createOkhttpClient() =  OkHttpClient.Builder()
    //.addInterceptor(httpLoggingInterceptor())
    .connectTimeout(30, TimeUnit.SECONDS)
    .readTimeout(30, TimeUnit.SECONDS)
    .writeTimeout(30, TimeUnit.SECONDS)
    .build()


//imprimir request/responde no log automaticamente
//private fun httpLoggingInterceptor() = HttpLoggingInterceptor().setLevel(HttpLoggingInterceptor.Level.BODY)

fun createUserService() = Retrofit.Builder()
    .baseUrl(BASE_URL)
    .client(createOkhttpClient())
    .addConverterFactory(GsonConverterFactory.create())
    .build()
    .create(UserWebService::class.java)

