package praticas.irent

import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import kotlinx.android.synthetic.main.activity_login.*
import org.jetbrains.anko.indeterminateProgressDialog
import org.jetbrains.anko.startActivity
import praticas.irent.model.GetPeopleResult
import praticas.irent.webservice.createUserService
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class LoginActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContentView(R.layout.activity_login)

        retrieveText.setOnClickListener {
            startActivity<RecuperarSenhaActivity>()
        }

        enterButton.setOnClickListener {
            startActivity<MainActivity>()
//            entrar()
        }
        registerText.setOnClickListener {
            startActivity<TelaCadastro>()
        }
    }

    private fun entrar() {
        val progress = indeterminateProgressDialog("Carregando, aguarde...")
        createUserService().getUser()
            .enqueue(object : Callback<GetPeopleResult> {
                override fun onFailure(call: Call<GetPeopleResult>, t: Throwable) {
                    progress.hide()
                    Log.d("Login: OnFailure", t.message)
                }

                override fun onResponse(call: Call<GetPeopleResult>, response: Response<GetPeopleResult>) {
                    progress.hide()
                    Log.d("Login: onResponse", response.body().toString())
                }
            })
    }

}
