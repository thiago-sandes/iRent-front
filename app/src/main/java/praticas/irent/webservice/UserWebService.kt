package praticas.irent.webservice

import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import praticas.irent.Request
import praticas.irent.TokenResponse
import praticas.irent.model.GetPeopleResult
import praticas.irent.model.People
import retrofit2.Call
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.Path
import java.util.concurrent.TimeUnit

interface UserWebService {

    @POST("sessions")
    fun userLogin(@Body request: Request): Call<TokenResponse>

    @POST("users")
    fun userSignUp(@Body request: Request): Call<TokenResponse>

}


const val BASE_URL = "https://young-lake-11756.herokuapp.com/"

fun createOkhttpClient() =  OkHttpClient.Builder()
    .addInterceptor(httpLoggingInterceptor())
    .connectTimeout(30, TimeUnit.SECONDS)
    .readTimeout(30, TimeUnit.SECONDS)
    .writeTimeout(30, TimeUnit.SECONDS)
    .build()


//imprimir request/responde no log automaticamente
private fun httpLoggingInterceptor() = HttpLoggingInterceptor().setLevel(HttpLoggingInterceptor.Level.BODY)

fun createUserService() = Retrofit.Builder()
    .baseUrl(BASE_URL)
    .client(createOkhttpClient())
    .addConverterFactory(GsonConverterFactory.create())
    .build()
    .create(UserWebService::class.java)