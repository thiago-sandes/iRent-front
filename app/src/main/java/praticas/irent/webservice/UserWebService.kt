package praticas.irent.webservice

import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import praticas.irent.model.GetPeopleResult
import praticas.irent.model.People
import retrofit2.Call
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.GET
import retrofit2.http.Path
import java.util.concurrent.TimeUnit

interface UserWebService {

    @GET(USER_PATH)
    fun getUser(): Call<GetPeopleResult>

    @GET("$USER_PATH/{$USER_ID_PATH}")

    fun getUser(@Path(USER_ID_PATH) id: Int): Call<People>

}

const val USER_PATH = "people"
const val USER_ID_PATH = "id"

//const val BASE_URL = "https://swapi.co/api/"

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