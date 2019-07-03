package praticas.irent

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import kotlinx.android.synthetic.main.activity_recuperar_senha.*
import org.jetbrains.anko.toast
import praticas.irent.extension.doAfterTextChanged
import praticas.irent.extension.isEmail
import praticas.irent.webservice.UserWebService
import praticas.irent.webservice.createUserService
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

        val req: UserRecuperarSenha = UserRecuperarSenha(email)
        val service: UserWebService = createUserService()
        val response: Call<TokenResponseRecuperarSenha>? = service.recuperarSenha(req)

        response?.enqueue(object : Callback<TokenResponseRecuperarSenha> {
            override fun onResponse(
                call: Call<TokenResponseRecuperarSenha>,
                response: Response<TokenResponseRecuperarSenha>
            ) {
                if (response.body().toString() == "null") {
                    toast("Email não cadastrado")
                } else {
                    toast("Email eviado")
                    finish()
                }

                //toast(response.body().toString())
            }

            override fun onFailure(call: Call<TokenResponseRecuperarSenha>, t: Throwable) {
                //toast(t.message.toString())
                toast("Ocorreu erro ao enviar email")

            }

        })
    }

}
