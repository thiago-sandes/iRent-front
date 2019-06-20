package praticas.irent.Retrofit

import retrofit2.Call
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.GET
import retrofit2.http.Path


fun RetrofitConfig(){
    val retrofit = Retrofit.Builder()
        .baseUrl("https://api.github.com/")
        .addConverterFactory(GsonConverterFactory.create())
        .build()

}
interface EmailService {
    @GET("email/{email}/json")
    fun listRepos(@Path("email") email: String)
}
