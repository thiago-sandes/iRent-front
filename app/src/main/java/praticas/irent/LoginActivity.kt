package praticas.irent

import android.media.session.MediaSession
import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import kotlinx.android.synthetic.main.activity_login.*
import okhttp3.ResponseBody
import org.jetbrains.anko.startActivity
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class LoginActivity : AppCompatActivity() {
    //VARIAVEL PRA GUARDAR O RETROFIT
    var service: Api? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContentView(R.layout.activity_login)

        retrieveText.setOnClickListener {
            startActivity<RecuperarSenhaActivity>()
        }

        //AQUI A GENTE VAI INSTANCIAR O RETROFIT, PASSANDO O CAMINHO DO HEROKU.
        val retrofit: Retrofit = Retrofit.Builder()
            .baseUrl("http://young-lake-11756.herokuapp.com/")
            .addConverterFactory(GsonConverterFactory.create())
            .build()

        service = retrofit.create(Api :: class.java)

        enterButton.setOnClickListener {
            entrar("santos_silva1997@hotmail.com", "123456")
        }
    }

    private fun entrar(email: String, senha: String){
        //AQUI A GENTE INSTANCIA UM OBJETO DO TIPO REQUEST E PASSA OS VALORES PRO CONSTRUTOR
        var req: Request = Request("ric", "ricardo","joelio@gmail.com", "9847447","848484", "M")
        var response: Call<ResponseBody>? = service?.userLogin(req)

        response?.enqueue(object : Callback<ResponseBody> {
            override fun onResponse(call: Call<ResponseBody>, response: Response<ResponseBody>) {
                Toast.makeText(getApplicationContext(), "Entrou", Toast.LENGTH_SHORT).show()
                Toast.makeText(getApplicationContext(), response.body().toString(), Toast.LENGTH_SHORT).show()
                //var token: ResponseBody? = response.body()
            }

            override fun onFailure(call: Call<ResponseBody>, t: Throwable) {
                Toast.makeText(getApplicationContext(), "RRR", Toast.LENGTH_SHORT).show()
            }


        })
    }

}











/*private fun entrar() {
        val progress = indeterminateProgressDialog("Carregando, aguarde...")
        createUserService().getUser()
            .enqueue(object : Callback<GetPeopleResult> {
                override fun onFailure(call: Call<GetPeopleResult>, t: Throwable) {
                    progress.hide()
                    Log.d("Login: OnFailure", t.message)
                }

                override fun onResponse(call: Call<GetPeopleResult>, response: TokenResponse<GetPeopleResult>) {
                    progress.hide()
                    Log.d("Login: onResponse", response.body().toString())
                }
            })
    }*/
