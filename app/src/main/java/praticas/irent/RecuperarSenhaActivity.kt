package praticas.irent

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import kotlinx.android.synthetic.main.activity_recuperar_senha.*
import org.jetbrains.anko.toast
import praticas.irent.extension.doAfterTextChanged
import praticas.irent.extension.isEmail
import praticas.irent.model.UserRecuperarSenha
import praticas.irent.webservice.postRecuperarSenha
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class RecuperarSenhaActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_recuperar_senha)

        setSupportActionBar(recuperarToolbar)

        recuperarSenhaEmailEditText.doAfterTextChanged {
            validateEmail(it)
        }

        recuperarToolbar.setNavigationOnClickListener {
            finish()
        }

        buttonRecuperar.setOnClickListener {
            val email = recuperarSenhaEmailEditText.text.toString()
            recuperar(email)
        }
    }

    private fun validateEmail(it: String) = it.isEmail().also {
        buttonRecuperar.isEnabled = it
        recuperarSenhaEmailInputLayout.error = if (!it) "Email inválido" else null
    }

    private fun recuperar(email: String) {

        postRecuperarSenha().recuperarSenha(email)
            .enqueue(object : Callback<UserRecuperarSenha> {
                override fun onFailure(call: Call<UserRecuperarSenha>, t: Throwable) {
                    toast(t.message.toString())
                    //toast("Email não cadastrado")

                }

                override fun onResponse(call: Call<UserRecuperarSenha>, response: Response<UserRecuperarSenha>) {
                    toast(response.body().toString())
                    //toast("Email enviado")
                }
            })

    }

}
