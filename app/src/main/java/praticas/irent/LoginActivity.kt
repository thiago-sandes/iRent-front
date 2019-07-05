package praticas.irent

import android.os.Bundle
import android.widget.EditText
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import kotlinx.android.synthetic.main.activity_login.*
import org.jetbrains.anko.startActivity
import praticas.irent.model.Request
import praticas.irent.model.TokenResponse
import praticas.irent.webservice.UserWebService
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

        registerText.setOnClickListener {
            startActivity<TelaCadastro>()
        }

        enterButton.setOnClickListener {
            var emailView = findViewById<EditText>(R.id.emailText)
            var senhaView = findViewById<EditText>(R.id.passText)
            entrar(emailView.text.toString(), senhaView.text.toString())
        }
    }

    //Faz a verificacao dos dados no servidor. Se estiver tudo ok, entra na proxima atividade.
    private fun entrar(email: String, senha: String){
        var req: Request = Request(email, senha)
        var service: UserWebService = createUserService()
        var response: Call<TokenResponse>? = service.userLogin(req)

        response?.enqueue(object : Callback<TokenResponse> {
            override fun onResponse(call: Call<TokenResponse>, response: Response<TokenResponse>) {
                if(response.body().toString() == "null")
                    Toast.makeText(
                        applicationContext,
                        "E-mail ou senha incorretos. Tente novamente.",
                        Toast.LENGTH_SHORT
                    ).show()
                else
                    startActivity<MainActivity>()
            }

            override fun onFailure(call: Call<TokenResponse>, t: Throwable) {
                Toast.makeText(applicationContext, "Ocorreu um erro no login", Toast.LENGTH_SHORT).show()
            }


        })
    }

}












