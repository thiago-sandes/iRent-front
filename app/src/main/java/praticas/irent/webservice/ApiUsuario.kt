package praticas.irent.webservice

import okhttp3.OkHttpClient
import okhttp3.ResponseBody
import praticas.irent.model.RequestOferta
import praticas.irent.model.RequestUsuario
import praticas.irent.model.TokenResponse
import retrofit2.Call
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit
import okhttp3.RequestBody
import praticas.irent.model.RequestImages
import retrofit2.http.*


interface ApiUsuario {

    @GET("sessions")
    fun verificaLogado(@Body request: RequestUsuario): Call<ResponseBody>

    @Multipart
    @POST("oferta/"+10+"images")
    fun uploadImages(@Body request: RequestImages): Call<ResponseBody>

    @POST("users")
    fun userCadastro(@Body request: RequestUsuario): Call<ResponseBody>

    @POST("oferta")
    fun ofertaCadastro(@Body request: RequestOferta): Call<ResponseBody>

}

const val BASE_URL_USER = "https://young-lake-11756.herokuapp.com/"

fun createOkhttpClient1() =  OkHttpClient.Builder()
    //.addInterceptor(httpLoggingInterceptor())
    .connectTimeout(30, TimeUnit.SECONDS)
    .readTimeout(30, TimeUnit.SECONDS)
    .writeTimeout(30, TimeUnit.SECONDS)
    .build()



//imprimir request/responde no log automaticamente
//private fun httpLoggingInterceptor() = HttpL().setLevel(HttpLoggingInterceptor.Level.BODY)

fun criarServicoUsuario() = Retrofit.Builder()
    .baseUrl(BASE_URL_USER)
    .client(createOkhttpClient1())
    .addConverterFactory(GsonConverterFactory.create())
    .build()
    .create(ApiUsuario::class.java)