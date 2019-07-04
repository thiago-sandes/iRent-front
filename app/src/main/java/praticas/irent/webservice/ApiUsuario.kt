package praticas.irent.webservice

import okhttp3.ResponseBody
import okhttp3.OkHttpClient
//import okhttp3.logging.HttpLoggingInterceptor
import praticas.irent.RequestUsuario
import retrofit2.Call
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.Path
import java.util.concurrent.TimeUnit

interface ApiUsuario {

    @POST("users")
    fun userCadastro(@Body request: RequestUsuario): Call<ResponseBody>

}

const val BASE_URL = "https://young-lake-11756.herokuapp.com/"

/*fun createOkhttpClient() =  OkHttpClient.Builder()
    //.addInterceptor(httpLoggingInterceptor())
    .connectTimeout(30, TimeUnit.SECONDS)
    .readTimeout(30, TimeUnit.SECONDS)
    .writeTimeout(30, TimeUnit.SECONDS)
    .build()
*/


//imprimir request/responde no log automaticamente
//private fun httpLoggingInterceptor() = HttpL().setLevel(HttpLoggingInterceptor.Level.BODY)

fun createUserService() = Retrofit.Builder()
    .baseUrl(BASE_URL)
    //.client(createOkhttpClient())
    .addConverterFactory(GsonConverterFactory.create())
    .build()
    .create(ApiUsuario::class.java)